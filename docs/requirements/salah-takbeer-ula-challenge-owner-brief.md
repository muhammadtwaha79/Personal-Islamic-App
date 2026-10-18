# Owner Requirements Capture: Daily Salah, Jamaat, Takbeer-e-Ula and 40-Day Challenge

*Received from the owner on 2026-10-18 in a session brief. Status: **CAPTURED, NOT YET ARCHITECTED**. These are owner product requirements, not accepted ADRs and not religious rulings. They are scheduled for Phase 2 planning (brief steps 5 to 12) and must not be implemented before the Phase 1 verification gate passes and the owner approves the Phase 1 merge. All religious content here starts `UNVERIFIED` (see [../fiqh/rule-register.md](../fiqh/rule-register.md)).*

## 1. Tracking program

- Personal tracking start date: **2026-10-20** (a user-configurable personal setting, not an assertion about historical worship).
- Must support: manual tracking, historical backfill, corrections, timezone-aware local dates, midnight boundaries, device restarts, process termination, offline persistence.
- If installed after the start date, the user can backfill earlier records manually. Never auto-mark earlier dates completed; never fabricate congregation or Takbeer-e-Ula results.

## 2. Daily occurrences

- Fajr 2, Dhuhr 4, Asr 4, Maghrib 3, Isha 4 (Fard rakah counts as stated by the owner).
- Each day has five logical occurrences with **stable identifiers**; never two occurrences for the same (date, prayer). New occurrences start `Pending` or `Unknown` per the approved domain state model. Unanswered is never auto-classified as missed.

## 3. Congregation (stored separately from completion state)

- Mode: `WITH_JAMAAT`, `INDIVIDUAL`, `UNKNOWN`, `NOT_RECORDED`.
- If `WITH_JAMAAT`: `JOINED_FROM_BEGINNING`, `JOINED_LATE`, `UNCERTAIN`.
- Individual prayer is still a completed prayer (subject to validity information). The database never infers that individual means missed.

## 4. Takbeer-e-Ula field

- States: `ACHIEVED`, `NOT_ACHIEVED`, `UNKNOWN`, `NOT_APPLICABLE`.
- Mode `INDIVIDUAL` implies `NOT_APPLICABLE`. Mode `UNKNOWN` never silently implies `ACHIEVED`; the user must confirm.
- **Religious safety:** the exact definition may need scholarly clarification. Joining the first rakah must not be silently equated with catching the opening takbir. The definition must be documented and **configurable**, with a strict literal opening-takbir option where appropriate. Unresolved interpretations are flagged for scholarly review. No invented Hadith references or reward guarantees.

## 5. Challenge: "40 Days of Takbeer-e-Ula"

- Start 2026-10-20, 40 consecutive calendar days, 5 opportunities per day (200 total).
- A day is **successful** only if all five prayers have **confirmed** Takbeer-e-Ula `ACHIEVED`. Unknown is not success. Individual prayer is not Takbeer-e-Ula.
- Two separate measures that must never be conflated: (A) **consecutive successful days**; (B) **cumulative achieved opportunities out of 200**.
- Also track: challenge day number, calendar date, per-prayer status, unknown results, missed opportunities, historical corrections.
- A failed day interrupts the consecutive streak accurately; history is never erased; the challenge never restarts silently; restart is **user-confirmed** and earlier attempts stay in history.
- Challenge status and Salah validity are separate: a failed challenge day never implies an invalid prayer.
- The challenge references canonical prayer occurrences; it does not duplicate prayer history. A historical correction must correctly recompute derived challenge statistics (deterministic reconstruction).
- Personal discipline tool only. No exaggerated gamification. Never implies acceptance of worship by Allah. Not a fatwa and not a verified religious guarantee.

## 6. Data model guidance from the owner (suggested entities)

`PrayerOccurrence`, `PrayerCompletionRecord`, `CongregationRecord`, `TakbeerUlaRecord`, `ChallengeDefinition`, `ChallengeAttempt`, `ChallengeProgress`, `PrayerRecordRevision`. Practical normalization; uniqueness constraints; transactional and idempotent writes; stable IDs; history-preserving corrections; migration safety; no silent data loss.

## 7. UI guidance

Premium daily dashboard with five cards (name, completion, congregation, Takbeer-e-Ula, edit action, optional note), clearly distinguishing confirmed from unknown data. A dedicated 40-day dashboard (day 1 to 40, five-prayer status, progress, historical attempts, gentle encouragement).

## 8. Required test areas

Prayer recording; congregation transitions; Takbeer-e-Ula validation; 40-day boundaries; incomplete and successful days; consecutive-day calculation; restart; historical corrections reconstructing history; duplicate prevention; process-restart persistence; timezone changes; database migrations; unknown states; no daily success while any of five prayers is unconfirmed. Tests are never weakened to obtain green CI.

## 9. Open questions found while capturing (to resolve in Phase 2 step 5; none block Phase 1)

- **Q1 Friday (Jumu'ah).** The brief lists Dhuhr as 4 Fard. On Fridays Jumu'ah is normally prayed instead of Dhuhr. How the Dhuhr occurrence, the congregation field and the challenge treat Fridays is a religious and product decision for the owner and a qualified reviewer. `UNVERIFIED`.
- **Q2 Excused or non-applicable days.** The challenge requires five-of-five confirmed days. The brief does not say how days of travel, illness or other circumstances are represented (counted as failed, excluded, or flagged). Owner decision needed.
- **Q3 Date of an occurrence.** Proposed (not approved): an occurrence belongs to the local calendar date on which its prayer window is scheduled, not the date it is logged, so late-night logging of Isha does not move it. Needs owner confirmation and a rule-register entry.
- **Q4 State model reconciliation.** The brief refers to "the approved domain state model". It must be reconciled with the existing Salah models in [../domain/](../domain/) and the Qaza ledger design before schema work; that reconciliation has not been done.
- **Q5 Takbeer-e-Ula definition options.** Candidate definitions (strict opening takbir versus a looser rule) are to be written into the rule register as `UNVERIFIED` options with no default adopted until the owner chooses.
