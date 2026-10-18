# Product Requirements

Status: Phase 0 draft. Requirement IDs are stable; priority: **M** must, **S** should, **C** could. All requirements are *planned*, none implemented.

## Salah (Fard)

| ID | Pri | Requirement |
| --- | --- | --- |
| FR-SAL-001 | M | Track Fajr (2), Dhuhr (4), Asr (4), Maghrib (3), Isha (4) independently per day. |
| FR-SAL-002 | M | Per-prayer states: Pending, Completed in preferred time, Completed late within window, Missed, Completed as Qaza, Unknown, Excused. |
| FR-SAL-003 | M | A prayer is never marked Missed merely because a notification was ignored or a window ended; past-window Pending is shown as "needs your review" (derived, not stored). |
| FR-SAL-004 | M | Record separately: occurrence date, window start/end (with calculation settings snapshot), completion time, record creation time, last manual correction time. |
| FR-SAL-005 | M | Records are editable; every edit is stored as an immutable revision with reason; accounting effects follow `qaza-accounting.md`. |
| FR-SAL-006 | M | Moving a missed prayer to Qaza is an explicit user action that creates exactly one ledger entry (idempotent). |

## Sunnah and Nafl

| ID | Pri | Requirement |
| --- | --- | --- |
| FR-SUN-001 | M | User explicitly selects and confirms a fiqh profile (initial: Hanafi). No silent default across schools. |
| FR-SUN-002 | M | Profile-driven Sunnah table (Muakkadah, Ghair Muakkadah, Nafl, Witr) per `rule-register.md`. |
| FR-SUN-003 | M | Voluntary prayers (Tahajjud, Ishraq, Duha/Chasht, Awwabeen, Tawbah, Tahiyyat al-Wudu, Tahiyyat al-Masjid, general Nafl) tracked individually with configurable rakah counts. |
| FR-SUN-004 | S | Frequency history, daily/weekly review, optional reminders, contextual guidance (only from verified register rules). |
| FR-SUN-005 | M | Missing voluntary worship never creates Qaza. (Exceptions, if any, are a register decision, not code default.) |

## Qaza recovery

| ID | Pri | Requirement |
| --- | --- | --- |
| FR-QAZ-001 | M | Append-only ledger is the sole source of truth for balances (see `qaza-accounting.md`). |
| FR-QAZ-002 | M | Per-category opening estimates, independently editable *by appending entries* (Fajr, Dhuhr, Asr, Maghrib, Isha; Witr only if profile requires it and user confirms). |
| FR-QAZ-003 | M | Entry types: opening estimate, discovered obligation, completed Qaza, correction, reversal, reconciliation. |
| FR-QAZ-004 | M | Atomic, idempotent writes; no duplicate deductions; no negative balances. |
| FR-QAZ-005 | M | Show: original estimate, completed, remaining, progress %, daily and weekly progress, history. |
| FR-QAZ-006 | M | Daily recovery plans: N per day, one per category per day, custom per-category. |
| FR-QAZ-007 | S | Plan-pace projection clearly labelled as conditional; suppressed when data is insufficient. No fabricated dates. |
| FR-QAZ-008 | M | Estimates are never converted into invented per-day prayer records. |

## Fasting

| ID | Pri | Requirement |
| --- | --- | --- |
| FR-FST-001 | M | Distinguish Fard (Ramadan), Qaza, Voluntary, and other categories. |
| FR-FST-002 | M | Ramadan daily tracking; completed, missed, uncertain historical obligations; Qaza fasts on the shared obligation ledger. |
| FR-FST-003 | S | Voluntary plans: Mondays, Thursdays, 13/14/15 Hijri, six Shawwal, Arafah, Ashura and associated Muharram days, custom plans. |
| FR-FST-004 | M | Configurable Hijri calendar with user day-offset; never assume calculated dates equal local sighting. |
| FR-FST-005 | M | Warn on days where fasting is restricted (per register). |
| FR-FST-006 | M | No automatic Kaffarah or other liability. |

## Cross-cutting

| ID | Pri | Requirement |
| --- | --- | --- |
| FR-X-001 | M | Fully functional offline; no mandatory account; no ads; no analytics by default. |
| FR-X-002 | M | English and Roman Urdu UI; architecture ready for Urdu script and RTL. |
| FR-X-003 | M | Light and dark themes; deep emerald, warm ivory, subtle gold; accessible contrast and TalkBack labels. |
| FR-X-004 | S | Encrypted backup, safe restore, manual export, optional app lock (Phase 8). |
| FR-X-005 | M | Minimal permissions; each added permission requires an ADR. |

## Non-functional requirements

- **Integrity:** zero tolerated data loss; migrations are tested against exported schemas.
- **Determinism:** balances and statistics are pure functions of stored data.
- **Performance:** dashboard cold render from local DB without perceptible delay on mid-range hardware (concrete budgets set in Phase 9).
- **Accessibility:** WCAG-style contrast, scalable text, TalkBack semantics.
- **Reproducibility:** any commit builds in GitHub Actions from a clean runner.

## Acceptance principle

A requirement is *done* only when implemented, covered by automated tests that ran in CI, and recorded as verified in `ENGINEERING_STATUS.md`.
