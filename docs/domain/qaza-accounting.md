# Qaza Accounting: Append-Only Ledger

Status: design (Phase 0, revised after owner review 2026-10-17). Implementation is Phase 4. This is the most important correctness surface in the product.

## 1. Why a ledger, not a counter

An editable "remaining = 365" counter loses history, cannot be audited, and a single bug or double-tap corrupts it permanently. A ledger stores *events*; balances are always recomputed from them. A bug can then be diagnosed and corrected by appending entries, never by rewriting the past.

## 2. Scope and categories

The ledger is generic over `obligation_kind` (ADR-0006): `SALAH_FARD`, `SALAH_WITR`, and later `RAMADAN_FAST`. Phase 4 delivers Salah; Phase 5 reuses the engine for fasting Qaza.

Categories: Fajr, Dhuhr, Asr, Maghrib, Isha under `SALAH_FARD`. **Witr is a separate, configurable category (`SALAH_WITR`)** that is never added automatically (ADR-0015): it exists only after the user enables it through an explicit confirmation record (section 4, L11).

**Setup suggestion (not data):** onboarding may pre-fill an editable estimate of 365 per Fard category (the owner's planning figure, not a verified record). Nothing is written to the ledger until the user confirms each number. After enabling Witr, the same editable suggestion may be offered for it.

## 3. Entry model

Table `ledger_entry` (names provisional):

| Column | Meaning |
| --- | --- |
| `seq` | Monotonic integer primary key assigned by the database; defines display order. |
| `entry_id` | UUID, stable external identifier. |
| `obligation_kind`, `category` | e.g. `SALAH_FARD` + `FAJR`. |
| `entry_type` | `OPENING_ESTIMATE`, `DISCOVERED_OBLIGATION`, `QAZA_COMPLETED`, `CORRECTION_ADJUSTMENT`, `REVERSAL`, `RECONCILIATION_ADJUSTMENT`. |
| `delta` | Signed non-zero integer. `OPENING_ESTIMATE`, `DISCOVERED_OBLIGATION`: `> 0`. `QAZA_COMPLETED`: `< 0`. `CORRECTION_ADJUSTMENT`, `RECONCILIATION_ADJUSTMENT`: either sign, **reason mandatory**. `REVERSAL`: `-target.delta`. |
| `adjusts` | Nullable enum. `ESTIMATE` marks a manual estimate correction (so the original estimate can be derived). |
| `source` | `USER_ESTIMATE`, `USER_DISCOVERY`, `USER_COMPLETION`, `DAILY_RECORD`, `USER_CORRECTION`, `SYSTEM_REVERSAL`, `IMPORT`. |
| `source_ref` | Optional id of the daily record / revision / import batch that caused the entry. |
| `idempotency_key` | Unique. The same key submitted twice produces no second row. |
| `reverses_entry_id` | Nullable, **unique**; set only on `REVERSAL`. |
| `fulfills_record_id` | Optional: a completion tied to a specific dated missed prayer. |
| `certainty` | `ESTIMATED` or `USER_CONFIRMED`. |
| `period_note` | Optional free text for the period an estimate covers. Never parsed into dates. |
| `occurred_on` | Local civil date the user says this applies to. |
| `recorded_at`, `zone_id` | UTC instant of insertion and zone at the time. |
| `fiqh_profile`, `register_version` | Profile and register version in force when the entry was created. |
| `confirmation_id` | Required for `SALAH_WITR` entries: references the active category confirmation. |
| `reason`, `note` | Free text; `reason` mandatory for corrections and reconciliations. |
| `schema_version` | Row semantics version. |

No column is ever updated after insertion. A separate small table `category_confirmation` (also append-only) records enabling or disabling a category with timestamp, profile and register version.

## 4. Invariants

- **L1 Append-only.** `UPDATE` and `DELETE` on `ledger_entry` are rejected by database triggers (`RAISE(ABORT)`), not only by application code. The DAO exposes plain `INSERT` with abort/ignore semantics only; `REPLACE`/upsert are forbidden on this table (a REPLACE conflict would delete a row and could bypass delete triggers) and an architecture test enforces it. `PRAGMA recursive_triggers` is also set as defense in depth.
- **L2 Non-negative balance.** For every `(obligation_kind, category)`, `SUM(delta) >= 0` after every committed transaction. Enforced in the domain *and* by an insert trigger so no code path can bypass it.
- **L3 Idempotency.** `idempotency_key` is `UNIQUE`. A retry returns the existing result.
- **L4 Single reversal.** A reversal references an existing non-reversal entry of the same kind and category with `delta = -target.delta`; each entry can be reversed at most once.
- **L5 Corrections are composite or signed.** An estimate correction is a single signed `CORRECTION_ADJUSTMENT` with `adjusts = ESTIMATE` and a reason. Reversing a wrong entry and adding a right one is done in one transaction. No in-place edit exists.
- **L6 Atomicity.** Ledger insert(s), related daily-record revision, and plan side effects commit in one database transaction or not at all.
- **L7 Determinism.** Balance = `SUM(delta)` grouped by kind and category. No dependency on wall clock, entry order or cached values. Any cache is disposable and verified against the sum.
- **L8 Explicit provenance.** `source` is mandatory; entries with unknown origin are rejected.
- **L9 No fabricated dates.** Estimates are never expanded into dated prayer rows.
- **L10 Migration safety.** Migrations never change the meaning of existing rows, never use destructive fallback, and never recreate the ledger table without a test proving row-for-row preservation; exported schemas are committed; every migration has a test loading a populated prior-version database and re-verifying balances. Triggers are created in migrations and in the test database setup.
- **L11 Witr gating.** A `SALAH_WITR` entry requires a `confirmation_id` pointing to an active, user-made confirmation. Without it the insert is rejected by the domain and by a database check.
- **L12 Controlled erase.** "Erase all data" and restore replace the database through a dedicated, explicit, user-confirmed path (staging database, verification, swap). They never rely on bypassing triggers in the live database.

## 5. Operations

| Operation | Effect |
| --- | --- |
| Set initial estimate | Append `OPENING_ESTIMATE` (`delta > 0`). |
| Correct estimate to N | Compute difference from the current net estimate; append one `CORRECTION_ADJUSTMENT` (`adjusts = ESTIMATE`, reason required). A decrease is bounded by L2 (see open item O1). |
| Discover more obligations | Append `DISCOVERED_OBLIGATION`. |
| Complete Qaza (n) | Append `QAZA_COMPLETED` with `delta = -n`; rejected atomically if balance `< n`. Order-agnostic (FQ-004 not enforced). |
| Undo a completion | `REVERSAL` of that entry (always allowed; balance increases). |
| Daily missed -> Qaza | Explicit user action; key `daily-miss:{recordId}:{prayer}`; one `DISCOVERED_OBLIGATION` (+1, source `DAILY_RECORD`). |
| Daily record later corrected to completed | In the same transaction as the revision, append `REVERSAL` (source `SYSTEM_REVERSAL`). If that would breach L2, the correction is blocked with a clear message; no silent partial state. |
| Enable Witr | Append a `category_confirmation` record (explicit user action); only then may Witr entries be written. |
| Reconcile | User-confirmed `RECONCILIATION_ADJUSTMENT`, mandatory reason, still bound by L2. |

Actions triggered outside the main UI (for example from a notification) carry a stable idempotency key per user action so a double delivery cannot double-count.

## 6. Derived figures (computed, never stored as truth)

- **Original estimate** = net of `OPENING_ESTIMATE` plus corrections with `adjusts = ESTIMATE`. The first-entered value is also viewable in history.
- **Total recorded obligation** = net of all obligation-adding and estimate-adjusting entries, after reversals.
- **Completed** = net of `QAZA_COMPLETED` (as a positive number), after reversals.
- **Remaining** = total recorded obligation - completed (equals `SUM(delta)`).
- **Progress %** = completed / total recorded obligation. It can *drop* when obligations are added; the UI says so and also shows progress against the original estimate.
- **Daily/weekly progress** = completions grouped by `occurred_on` in the user's local zone.

## 7. Recovery plans

`RecoveryPlan` is configuration (mutable, versioned), not accounting. Types: *N per day (any category)*, *one per category per day*, *custom per-category counts*. Plans never write ledger entries by themselves; only a user-confirmed completion does.

## 8. Projection policy (no misleading predictions)

- Show "at your plan pace, remaining would take about X days" only when a plan is active, and label it conditional.
- Show an observed-pace figure only with at least 14 days of recorded activity; show the window used. Otherwise show nothing.
- Never present a single confident completion date; show a range or the conditional statement. No projection when pace is zero.
- Copy never implies reward, rank or acceptance.

## 9. Assumptions (owner-reviewed 2026-10-17 where noted)

1. Qaza within one category is fungible; completions are not tied to specific dated misses unless the user links them.
2. Witr is a separate, configurable category and is never added without user confirmation (owner decision, FQ-003 still `UNVERIFIED`).
3. Qaza ordering is not enforced (owner decision, FQ-004 still `UNVERIFIED`).
4. The 365-per-category figures are the owner's planning estimates, not verified records; the app supports them as editable suggestions and supports manual corrections (owner decision).
5. How to estimate historical Qaza correctly is a religious question (FQ-014); the app records the user's number and does not compute it.

## 10. Open design items

- **O1: lowering an estimate below what is already completed.** L2 forbids negative balances, so such a correction is rejected. A way to treat surplus completions (for example reclassifying them as voluntary prayers via a reconciliation) is a Phase 4 design decision requiring owner input; until then the app explains the block and does nothing destructive.

## 11. Required test families (Phase 4)

Property-based balance tests; idempotent retry tests; concurrency tests (parallel completions cannot overdraw); trigger tests proving UPDATE/DELETE/REPLACE cannot alter or remove rows; reversal rules; composite correction atomicity (fault injection mid-transaction); Witr gating; estimate-correction math; migration tests with populated databases (including a test that fails if a migration drops or recreates the ledger without preserving rows); deterministic projection tests.
