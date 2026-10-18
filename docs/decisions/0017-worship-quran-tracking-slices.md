# ADR-0017: Non-prescriptive Salah, Sunnah, and Quran tracking slices

**Status:** Accepted for technical tracking model; religious interpretations remain UNVERIFIED.

## Context

The owner requested usable record keeping from **2026-10-20** for five daily Fard prayers, Jamaat, Takbeer-e-Ula, a 40-day challenge, voluntary prayers including Tahajjud/Ishraq/Chasht/Awwabeen and Sunnah before Asr/Isha, daily Quran half para plus Yaseen/Waqiah/Mulk, and five full khatams during Ramadan. The foundation is local, offline-first, with no connected fiqh reviewer.

## Decision

1. All records are user-entered facts, not assertions of Salah's religious validity. Unanswered occurrences are UNKNOWN, never automatically MISSED.
2. Each local civil date plus prayer name identifies one canonical five-prayer occurrence. Each Salah correction appends a full revision snapshot inside the same Room transaction as the updated current record. No obligation ledger entries are generated automatically.
3. Congregation, joined-late, and opening Takbeer state are recorded separately. The 40-day personal challenge is **2026-10-20 through 2026-11-28 inclusive**; per-prayer achievements and day streaks are derived, not a judgment of reward. Friday midday record can be self-labeled Jumu'ah or Dhuhr, with no automated religious ruling applied.
4. Sunnah/Nafl practice records have date, type, rakah count and optional completion. Listing a practice is not a claim it creates a qaza debt. Rule interpretations remain subject to scholar review.
5. Quran daily reading uses **half-juz units**, and Yaseen/Waqiah/Mulk are independent self-confirmed daily actions.
6. Ramadan start/end dates require explicit user entry. Reading volume is aggregated separately from **append-only, user-confirmed full Quran khatam events**, avoiding falsely inferring khatams from total pages or juz. Target five khatams (150 juz in reading volume) is aspirational, not evidence of completion.
7. Room schema migrations 1→2→3 are explicit, additive; destructive migration is forbidden. No backup is claimed until a future verified encrypted-backup implementation.

## Known limitations and future work

- Time-window classification, configurable Asr method, automatic prayer times, fiqh profile onboarding, and exact Takbeer-e-Ula scholarly criteria remain open.
- Current challenge is the initial fixed 40-day attempt; repeatable challenge-attempt management and excuse rules need separate review.
- JVM CI tests cover selected domain invariants. Android device/emulator persistence, migration tests and UI interaction tests remain pending; a green JVM build alone does not prove device reliability.
- Qaza ledger, fasting, Adhkar/Duas, notifications, accessibility hardening, encryption and exports remain future tracked work.

## Evidence

See [ENGINEERING_STATUS](../ENGINEERING_STATUS.md) and PR #3: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/3 for the actual current CI status. Religiously unverified interpretations remain in [rule register](../fiqh/rule-register.md).
