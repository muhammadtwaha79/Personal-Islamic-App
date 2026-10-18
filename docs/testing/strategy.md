# Test Strategy

Status: plan (Phase 0). No tests exist yet. Test tool choices and versions are fixed in Phase 1 from authoritative sources.

## 1. Philosophy

Integrity of obligation records outranks everything. The test suite is built outward from the Qaza ledger and religious-rule logic, then UI.

## 2. Pyramid

| Level | Scope | Runs in |
| --- | --- | --- |
| Unit (JVM) | Domain logic: ledger math, state rules, plan and projection logic, Hijri offset logic, rule-register parsing | CI `testDebugUnitTest` |
| Property-based | Ledger balance invariants over random operation sequences | CI (JVM) |
| Data/integration | Room DAOs, triggers, transactions, migrations on a real SQLite instance | CI (JVM-based Room testing where possible, otherwise emulator job) |
| UI | Compose UI tests for vertical slices, accessibility semantics | CI emulator job (Phase 2+) |
| End-to-end smoke | Critical flows on a real emulator | CI emulator job (Phase 9 hardening) |

## 3. Mandatory test families

**Ledger (Phase 4):** append-only enforcement via triggers (UPDATE/DELETE must fail); idempotent retries; overdraw prevention under concurrent writes; reversal rules (single reversal, matching delta, same category); composite correction atomicity with injected failure mid-transaction; deterministic balance and projection; migration tests that open populated older-version databases and re-verify balances.

**Salah (Phase 2):** state transition rules; `NEEDS_REVIEW` derivation; revision history immutability; occurrence-date vs instant handling across midnight and time-zone change.

**Fiqh (Phase 3):** rule-set selection by confirmed profile; no profile -> no Sunnah table; register version recorded.

**Fasting (Phase 5):** Hijri offset application; restricted-day warnings from register data; no automatic Kaffarah or Qaza from broken voluntary fasts.

**Privacy (Phase 8):** backup round-trip, tamper detection, wrong-passphrase handling.

## 4. Conventions

- Domain tests use fake `Clock` and `IdGenerator`.
- No test depends on wall-clock time, locale, or time zone of the runner (zone is injected).
- Test names state behavior. Flaky tests are fixed or removed with an ADR, never ignored.
- Room schema JSON is exported and committed; migration tests read it.

## 5. Quality gates in CI

Unit tests, lint, and build must pass. Coverage is reported; a numeric threshold for `domain/qaza` and `domain/salah` is set at Phase 2 and tightened later. Gates are never relaxed merely to pass.

## 6. Reporting

Status documents record only tests that actually ran, with the CI run or check-run reference. "Tests written but not run" is stated as such.
