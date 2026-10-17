# Qaza Accounting: Append-Only Ledger

Status: design (Phase 0). Implementation is Phase 4. This is the most important correctness surface in the product.

## 1. Why a ledger, not a counter

An editable "remaining = 365" counter loses history, cannot be audited, and a single bug or double-tap corrupts it permanently. A ledger stores *events*; balances are always recomputed from them. A bug can then be diagnosed and corrected by appending entries, never by rewriting the past.

## 2. Scope

The ledger is generic over `obligation_kind` (ADR-0006): `SALAH_FARD`, `SALAH_WITR` (only under a profile that requires it and after user confirmation), and later `RAMADAN_FAST`. Phase 4 delivers Salah; Phase 5 reuses the same engine for fasting Qaza.

## 3. Entry model

Table `ledger_entry` (names provisional):

| Column | Meaning |
| --- | --- |
| `seq` | Monotonic integer primary key, assigned by the database. Defines display order. |
| `entry_id` | UUID, stable external identifier. |
| `obligation_kind`, `category` | e.g. `SALAH_FARD` + `FAJR`. |
| `entry_type` | `OPENING_ESTIMATE`, `DISCOVERED_OBLIGATION`, `QAZA_COMPLETED`, `CORRECTION_ADJUSTMENT`, `REVERSAL`, `RECONCILIATION_ADJUSTMENT`. |
| `delta` | Signed non-zero integer. Obligation-adding types: `> 0`. `QAZA_COMPLETED`: `< 0`. |
| `source` | Explicit origin: `USER_ESTIMATE`, `USER_DISCOVERY`, `USER_COMPLETION`, `DAILY_RECORD`, `USER_CORRECTION`, `SYSTEM_REVERSAL`, `IMPORT`. |
| `source_ref` | Optional: id of the daily record / revision / import batch that caused the entry. |
| `idempotency_key` | Unique. Same key submitted twice = no second row. |
| `reverses_entry_id` | Nullable, **unique**; set only on `REVERSAL`. |
| `fulfills_record_id` | Optional: a completion tied to a specific dated missed prayer. |
| `certainty` | `ESTIMATED` or `USER_CONFIRMED`. Applies to obligation entries. |
| `period_note` | Optional free text describing the period an estimate covers. Never parsed into dates. |
| `occurred_on` | Local civil date the user says this applies to. |
| `recorded_at`, `zone_id` | UTC instant of insertion and zone at the time. |
| `note` | Optional text. |
| `schema_version` | Row semantics version. |

No column is ever updated after insertion.

## 4. Invariants

- **L1 Append-only.** `UPDATE` and `DELETE` on `ledger_entry` are rejected by database triggers (`RAISE(ABORT)`), not only by application code.
- **L2 Non-negative balance.** For every `(obligation_kind, category)`, `SUM(delta) >= 0` after every committed transaction. Enforced in the domain *and* by an insert trigger so no code path can bypass it.
- **L3 Idempotency.** `idempotency_key` is `UNIQUE`. A retry returns the existing result.
- **L4 Single reversal.** A reversal references an existing non-reversal entry of the same kind and category with `delta = -target.delta`; each entry can be reversed at most once (`reverses_entry_id` unique).
- **L5 Corrections are composite.** A "correction" is one transaction: a `REVERSAL` of the wrong entry plus a new entry, both carrying the same `source_ref` linkage. No in-place edit exists.
- **L6 Atomicity.** Ledger insert(s), related daily-record revision, and plan side effects commit in one database transaction or not at all.
- **L7 Determinism.** Balance = `SUM(delta)` grouped by kind and category. No dependency on wall clock, entry order, or cached values. Any cache is disposable and verified against the sum.
- **L8 Explicit provenance.** `source` is mandatory; entries with unknown origin are rejected.
- **L9 No fabricated dates.** Estimates are never expanded into dated prayer rows.
- **L10 Migration safety.** Migrations never change the meaning of existing rows; exported schemas are committed; every migration has a test that loads a populated prior-version database and re-verifies balances.

## 5. Operations

| Operation | Effect |
| --- | --- |
| Set/raise estimate | Append `OPENING_ESTIMATE` (or `DISCOVERED_OBLIGATION`) with `delta > 0`. |
| Lower an estimate | Append `REVERSAL` of the earlier entry, or a `RECONCILIATION_ADJUSTMENT` with required reason; blocked by L2 if completions already consume it. |
| Complete Qaza (n) | Append `QAZA_COMPLETED` with `delta = -n`; rejected atomically if balance `< n`. |
| Undo a completion | `REVERSAL` of that entry (always allowed; balance increases). |
| Daily missed -> Qaza | Explicit user action; key `daily-miss:{recordId}:{prayer}`; creates one `DISCOVERED_OBLIGATION` (+1, source `DAILY_RECORD`). |
| Daily record later corrected to completed | In the same transaction as the revision, append `REVERSAL` (source `SYSTEM_REVERSAL`). If that would breach L2, the correction is blocked and the user is told why; no silent partial state. |
| Reconcile | User-confirmed adjustment, any sign allowed only for this type, mandatory reason, still bound by L2. |

## 6. Derived figures (all computed, never stored as truth)

- **Original estimate** = net of `OPENING_ESTIMATE` entries.
- **Total recorded obligation** = net of all obligation-adding entries (including discovered), after reversals.
- **Completed** = net of `QAZA_COMPLETED` (as a positive number), after reversals.
- **Remaining** = total recorded obligation - completed (equals `SUM(delta)`).
- **Progress %** = completed / total recorded obligation. This can *drop* when new obligations are discovered; the UI states this and also shows progress against the original estimate separately.
- **Daily/weekly progress** = completions grouped by `occurred_on` in the user's local zone.

## 7. Recovery plans

`RecoveryPlan` is configuration (mutable, versioned), not accounting. Types: *N per day (any category)*, *one per category per day*, *custom per-category counts*. Plans never write ledger entries by themselves; only a user-confirmed completion does.

## 8. Projection policy (no misleading predictions)

- Show "at your plan pace, remaining would take about X days" only when a plan is active, and label it conditional.
- Show an observed-pace figure only with at least 14 days of recorded activity; show the window used. Otherwise show nothing.
- Never present a single confident completion date; show a range or the conditional statement. No projection when pace is zero.
- Copy never implies reward, rank or acceptance.

## 9. Assumptions (all open to owner review)

1. Qaza within one category is fungible; completions are not tied to specific dated misses unless the user links them.
2. Witr is a separate category *only* under a profile that treats it as obligatory (Hanafi) and after explicit user confirmation (FQ-003).
3. Rule on order of making up prayers (tartib) is not enforced in v1 (FQ-004).
4. The initial estimate of 365 per prayer (and 365 Witr) is a planning figure supplied by the owner, **not** a verified record.
5. How to estimate historical Qaza correctly is a religious question (FQ-014); the app records the user's number and does not compute it.

## 10. Required test families (Phase 4)

Property-based balance tests; idempotent retry tests; concurrency tests (parallel completions cannot overdraw); trigger tests proving UPDATE/DELETE fail; reversal rules; composite correction atomicity (fault injection mid-transaction); migration tests with populated databases; deterministic projection tests.
