# Roadmap

Status legend: Phase 0 is in review; Phases 1-9 are **planned, not started**. Dates are intentionally absent; ordering follows engineering dependencies. Each phase ends only when its Definition of Done is met and recorded in `ENGINEERING_STATUS.md`.

Dependency chain: 0 -> 1 -> 2 -> 3 -> 4; 5 needs 4 (shared ledger); 6 needs 2, 3, 5; 7 needs 2-5; 8 needs 4-5; 9 needs all. Phase 4 may be pulled ahead of Phase 3 if the owner prioritizes Qaza (the engine only needs Phase 1 plus the Salah record model from Phase 2).

---

## Phase 0: Product Architecture and Engineering Foundation

- **Objectives:** shared understanding of scope; documented architecture, ledger design, fiqh rule register, roadmap, CI plan; verified GitHub control.
- **Architecture:** `docs/architecture/overview.md`, ADRs 0001-0013.
- **Tasks:** audit tools; write all Phase 0 documents; open PR.
- **Acceptance:** all required files exist on the branch and were read back; PR open; open questions listed.
- **Testing:** document review only (no code).
- **Deliverables:** documents and PR.
- **Dependencies:** GitHub write access (verified).
- **Risks:** documents mistaken for implementation; unverified fiqh rules mistaken for rulings (mitigated by status labels).
- **Definition of Done:** owner reviews and merges the PR; owner answers or defers the escalated questions.

## Phase 1: Native Android Foundation and Cloud CI

- **Objectives:** compiling app skeleton and reproducible cloud pipeline.
- **Architecture:** single module, package boundaries, Hilt, Compose theme (emerald/ivory/gold, light and dark), navigation shell, Room database with schema export, locale scaffolding (English, Roman Urdu), `Clock`/`IdGenerator`.
- **Tasks:** resolve and document the toolchain matrix from official sources; bootstrap wrapper via workflow; `android-ci.yml`; manifest with minimal permissions and backup restrictions; architecture test; forbidden-file check; failure-report comment.
- **Acceptance:** a PR check run shows build, unit tests and lint passing; a debug APK artifact upload step succeeded.
- **Testing:** unit-test smoke, architecture test, migration test harness skeleton.
- **Deliverables:** Gradle project, workflows, `toolchain-matrix.md`.
- **Dependencies:** Phase 0 merged.
- **Risks:** version incompatibilities; no log tools (mitigated by failure comment); wrapper bootstrap friction.
- **Definition of Done:** green CI on a clean runner, status file updated with the real run reference.

## Phase 2: Five Daily Fard Salah Tracking

- **Objectives:** a complete vertical slice for daily Fard prayers.
- **Architecture:** `PrayerRecord`, revisions, window calculation behind an interface, Home and Daily Salah screens, onboarding step for calculation settings.
- **Tasks:** prayer-time computation approach decided by ADR (offline, settings snapshotted); states and derived `NEEDS_REVIEW`; edit with revision; history view; accessibility.
- **Acceptance:** user can log, correct and review all five prayers across days and midnight with data surviving process death and restart.
- **Testing:** state-transition and revision tests; Room integration tests; Compose UI tests for the main flow.
- **Deliverables:** working screens, persisted data, tests.
- **Dependencies:** Phase 1; rules FQ-005/006 decisions (or documented configurable fallback).
- **Risks:** prayer-time accuracy and method disputes; time-zone edge cases.
- **Definition of Done:** acceptance met with CI evidence; known limitations recorded.

## Phase 3: Sunnah and Nafl Prayer Engine

- **Objectives:** profile-confirmed Sunnah table and voluntary prayer tracking.
- **Architecture:** `FiqhProfile` confirmation, `SunnahRuleSet` from register data, `VoluntaryPrayerLog` and config.
- **Tasks:** onboarding profile confirmation; Sunnah/Nafl screen; per-type configurable rakah; weekly review; optional guidance text only from verified rules.
- **Acceptance:** no Sunnah table without a confirmed profile; logs persist and aggregate correctly; no Qaza created from voluntary misses.
- **Testing:** rule-set selection tests; aggregation tests; UI tests.
- **Deliverables:** Sunnah & Nafl slice, machine-readable register (versioned).
- **Dependencies:** Phase 2; owner/reviewer decisions on FQ-002, FQ-007 to FQ-011 (else inert/configurable).
- **Risks:** presenting unverified rulings.
- **Definition of Done:** acceptance met; each shown rule's status visible in code and docs.

## Phase 4: Qaza Recovery and Historical Obligation Ledger

- **Objectives:** the production-grade append-only ledger and recovery UI.
- **Architecture:** per `docs/domain/qaza-accounting.md`: entities, triggers, use cases, plans, projection policy.
- **Tasks:** migrations with triggers; operations (estimate, discover, complete, reverse, reconcile); daily-miss-to-Qaza linkage with composite transactions; recovery plans; progress and history screens; Witr category gated by profile.
- **Acceptance:** all invariants L1-L10 proven by tests; no path overdraws or double-counts; UI shows estimate, completed, remaining, progress and a non-misleading projection.
- **Testing:** property-based, concurrency, trigger, atomicity fault-injection and migration tests (see ledger doc section 10).
- **Deliverables:** ledger engine, Qaza screens, test suite.
- **Dependencies:** Phase 1 and the Phase 2 record model; FQ-003/004/014 decisions or flagged assumptions.
- **Risks:** subtle trigger or migration bugs; user confusion when percentages drop after new discoveries (explained in UI).
- **Definition of Done:** CI green with ledger test families passing; assumptions documented.

## Phase 5: Fasting and Hijri Calendar Engine

- **Objectives:** Ramadan, Qaza and voluntary fasting with a configurable Hijri calendar.
- **Architecture:** `HijriCalendar` abstraction, `FastRecord`, `FastingPlan`, `RAMADAN_FAST` on the shared ledger.
- **Tasks:** calendar method decision by ADR; offsets and overrides; fast tracking; plans; restricted-day warnings; Qaza fasts.
- **Acceptance:** dates correct under offsets; fasting types never conflated; no automatic Kaffarah or voluntary-fast Qaza.
- **Testing:** calendar offset tests; restricted-day tests; ledger reuse tests.
- **Deliverables:** Fasting and Calendar screens.
- **Dependencies:** Phase 4; FQ-015 to FQ-022.
- **Risks:** moon-sighting disagreement; rule uncertainty.
- **Definition of Done:** acceptance met; register statuses reflected.

## Phase 6: Notifications and Worship Planning

- **Objectives:** optional, respectful reminders and planning.
- **Architecture:** WorkManager scheduling (exact alarms only if justified), notification channels, calm copy.
- **Tasks:** prayer and fasting reminders; quiet hours; permission flows; reboot and time-change handling.
- **Acceptance:** reminders fire reliably within platform limits and never change records automatically.
- **Testing:** scheduler unit tests; instrumented tests for permission and reschedule behavior.
- **Deliverables:** settings and notification features.
- **Dependencies:** Phases 2, 3, 5.
- **Risks:** OEM battery restrictions; permission friction; lock-screen privacy.
- **Definition of Done:** documented reliability limits; no pressure-inducing copy.

## Phase 7: Progress Intelligence and Reflection

- **Objectives:** descriptive consistency insight and personal reflection.
- **Architecture:** read-only analytics over stored data; reflection notes.
- **Tasks:** weekly and monthly views; consistency summaries; reflection prompts.
- **Acceptance:** no language of rank, reward or divine acceptance; figures reproducible from data.
- **Testing:** deterministic statistics tests; copy review checklist.
- **Deliverables:** Progress and Reflection screens.
- **Dependencies:** Phases 2-5.
- **Risks:** streaks inducing anxiety (mitigated by optional, gentle framing).
- **Definition of Done:** copy reviewed against Principle 2.

## Phase 8: Data Security, Backup and Portability

- **Objectives:** encrypted backup, safe restore, export, optional app lock.
- **Architecture:** per `docs/security/privacy-model.md`; staging restore with invariant verification.
- **Tasks:** cryptographic ADR with sources; backup/restore; manual export; app lock; database-at-rest decision.
- **Acceptance:** round-trip restores identical balances; tamper and wrong-passphrase handling proven; claims in docs match implementation.
- **Testing:** backup round-trip, corruption, tamper, migration-on-restore tests.
- **Deliverables:** security features and updated privacy model.
- **Dependencies:** Phases 4, 5.
- **Risks:** cryptographic misuse; key loss (user education).
- **Definition of Done:** privacy model contains no unverified claims.

## Phase 9: Production Hardening and Release Readiness

- **Objectives:** reliability, performance, accessibility and release process.
- **Architecture:** no new features; hardening only.
- **Tasks:** full accessibility pass; performance budgets; emulator smoke tests; release signing via Actions secrets; release notes and install instructions.
- **Acceptance:** zero known data-integrity defects; accessibility checklist passed; reproducible signed release build.
- **Testing:** end-to-end smoke suite; migration chain tests across all versions.
- **Deliverables:** release candidate and documentation.
- **Dependencies:** Phases 1-8.
- **Risks:** late discovery of integrity or accessibility issues.
- **Definition of Done:** owner approves publication; nothing is published without that approval.
