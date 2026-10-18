# Architecture Risk Register

Review date: 2026-10-17 (Phase 0 owner review). Likelihood/Impact: L(ow), M(edium), H(igh). None of these risks has been tested yet.

| ID | Risk | L | I | Mitigation | Owner phase |
| --- | --- | --- | --- | --- | --- |
| R1 | **SQLite `REPLACE` can delete a row without firing delete triggers** (unless recursive triggers are on), silently bypassing ledger append-only protection. | M | H | Ledger DAOs expose plain inserts only; architecture test bans REPLACE/upsert on ledger; set `recursive_triggers`; trigger tests include REPLACE. | 4 |
| R2 | **Migrations can bypass triggers** (table recreation, DROP). Room auto-migrations do not know about triggers; destructive fallback would erase history. | M | H | Ban destructive fallback; manual migrations recreate triggers; migration tests on populated DBs prove row preservation. | 1, 4 |
| R3 | **"Erase all data" and restore conflict with append-only.** A naive implementation either fails or disables triggers on the live DB. | M | H | Dedicated controlled path: staging DB, verification, swap (L12). | 4, 8 |
| R4 | **Lowering an estimate below completed Qaza** is blocked by non-negative balance (O1). Users will hit this when correcting estimates. | H | M | Clear message now; surplus-handling design with owner input in Phase 4. | 4 |
| R5 | **No CI log tool in the connector.** Each compile error needs a CI round trip and may be hard to read. | H | M | Report comment with trimmed logs; tiny first scaffold; add dependencies incrementally; owner pastes logs as fallback. | 1 |
| R6 | **`GITHUB_TOKEN` commits do not retrigger workflows**, and `workflow_dispatch` needs the default branch. A naive wrapper bootstrap would stall. | H | M | Push-triggered bootstrap on a dedicated branch plus a follow-up connector commit (CI strategy section 3). | 1 |
| R7 | **Repository Actions permissions unknown.** Default workflow permissions may block the bootstrap push or PR comments. | M | M | Declare explicit job permissions; one-time owner settings check (Phase 1 plan). | 1 |
| R8 | **Toolchain incompatibility** (Gradle/AGP/Kotlin/KSP/Compose/Hilt/JDK/SDK). | M | M | Resolve matrix from official sources first; pin versions; one change at a time. | 1 |
| R9 | **Public artifacts and logs.** Debug APKs and logs in a public repo may be viewable by others. | L | M | No personal data in builds or logs; synthetic fixtures only; ADR-0012. | 1+ |
| R10 | **Roman Urdu (`ur` Latin script) locale support** in Android resources and per-app language settings is unverified. | M | L | Test in Phase 1; fall back to an in-app language switch. | 1 |
| R11 | **Occurrence-date vs calendar-date edge cases** (Isha after midnight, travel, DST). | M | H | ADR-0008 explicit occurrence date; fake-clock and zone tests. | 2 |
| R12 | **Prayer-time calculation accuracy and unresolved conventions** (high latitudes, twilight, Asr). | M | M | Explicit user settings, snapshots, no silent default (ADR-0015); verify against local timetables. | 2 |
| R13 | **Double-delivery of actions from notifications** could double-count a completion. | M | H | Idempotency key per user action (ledger L3). | 6 |
| R14 | **Progress percentage can drop** after discoveries, confusing the user or inducing anxiety. | M | L | Explain in UI; show progress against original estimate too; calm copy. | 4, 7 |
| R15 | **Android auto-backup / device transfer** could copy or overwrite the DB unexpectedly. | M | M | Disable or restrict via manifest and data-extraction rules in Phase 1; test. | 1 |
| R16 | **Profile switch after entries exist** could mislead about past obligations. | L | H | Entries carry profile and register version; switching never deletes entries. | 3, 4 |
| R17 | **Single-module boundaries erode** without enforcement. | M | L | Architecture test from Phase 1. | 1 |

## Gap found and fixed during this review

The first ledger design promised that entries record the profile in force but had no column for it, and had no mechanism to enforce "Witr only after user confirmation". Fixed in `qaza-accounting.md`: `fiqh_profile`, `register_version`, `confirmation_id`, a `category_confirmation` table and invariant L11.

The first CI design assumed the owner could trigger the wrapper bootstrap from the Actions tab; that depends on default-branch placement. Fixed in `ci-build-strategy.md` section 3.
