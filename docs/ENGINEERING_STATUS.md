# Engineering Status — Ibadah Companion

**Snapshot 2026-10-18. Always verify the live GitHub head and workflow before relying on any saved status.** [CLAUDE.md](../CLAUDE.md) · [Agent handoff](AGENT_HANDOFF.md) · [Session log](SESSION_LOG.md).

## Integrated and verified history

- PRs #1–6: merged Phase 0, native Android Phase 1, Fard/Nafl/Quran tracking, Salawat/Dhikr counters, and their documentation.
- **[MERGED] PR #7 — Source-linked Daily Masnoon Duas, Sunnah habits, Munajat-e-Maqbool**: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/7 — merge commit `baab2cf3320c3a9ab30982b88abc8d744c0199c2`.
- **[GREEN CI]** Final PR #7 source SHA `5960a199174914099824e869780f454430e5e9e3` passed [GitHub Actions run 39111339151](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39111339151), including Gradle wrapper validation, Kotlin compilation, unit tests, Android lint, debug APK assembly, tests report and APK artifact upload.
- [Artifact] `ibadah-companion-debug-apk`, GitHub artifact ID **12130677678**, artifact ZIP **12,216,635 bytes** (this is NOT the raw APK size or SHA-256). Expiration reported 2026-11-01 09:29:58 UTC (depending on GitHub retention settings).

## Implemented functionality merged to main

- Offline-first Kotlin/Compose/Material3, Hilt, Room schema **v5**, additive migration chain v1→2→3→4→5; no INTERNET permission, no analytics.
- Fard Salah user log (Jamaat/alone, Takbeer-e-Ula), 40-day self-tracking challenge starting **2026-10-20**, voluntary Sunnah/Nafl, Quran half-para/Yaseen/Waqiah/Mulk, manually selected Ramadan range and 5 confirmed Quran Khatams.
- Salawat/Dhikr: independent daily targets short Salawat 1200, Istighfar 1200, Bismillah 1000; user-provided **20** Arabic Salawat with 10 repetition goal each after every Fard; manually selected *Dala'il al-Khayrat* manzil.
- **New Masnoon module:** 28 source-linked Morning/Evening/Occasion/After-Salah dua/dhikr entries, including Sayyidul Istighfar, three Qul Surahs 3× each morning/evening (read entire surah from Mushaf), 3× Bismillahilladhi, 100× Subhanallahi wa bihamdihi, with independent date and scope counters; 10 source-linked situational Sunnah habits.
- Separate **Munajat-e-Maqbool** daily manzil 1–7 manual choice, edition label, complete/correct actions; no book text or automatic weekly mapping.
- Requested 7× Hasbiyallahu has **explicit disputed/criticized grading** (Abu Dawud 5081 listed Mawdu' by al-Albani), not counted toward ordinary sourced-masnoon completion; no promise of special reward.

## Limitations — do not overclaim

- **NOT VERIFIED on a physical Android phone.** Owner has not installed the app. No on-device persistent-record smoke test or populated Room migrations test v1–v5 yet.
- Personal records are in non-encrypted local SQLite; Android backup disabled and **no encrypted export/restore** implemented. Clearing storage or uninstalling the app can erase user records. Do not recommend it as the sole permanent historical ledger yet.
- Arabic diacritics/variant wordings, grade disputes and fiqh applications need qualified scholarly editorial review. Source-linked references are not independent certification of every letter or every religious rule.
- The curated 28+10 content is **not exhaustive**. Future source-led enrichment with Roman Urdu translations, optional customizable routines and reviewed material remains open.
- Qaza ledger, fasting/Hijri, full accessibility/instrumented testing, encrypted backup, app lock and reminders remain pending.

## Next exact action

1. Confirm [PR #8 postmerge docs](https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/8) (if it exists) is merged, or merge after green CI under the owner's standing authorization.
2. Ask owner to install the [final Masnoon APK from run 39111339151](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39111339151), download artifact ZIP, extract and install debug APK **without clearing older app storage if present**.
3. Owner tests Salah/Salawat/Quran/Dhikr/Masnoon/Munajat records and retention after restart; record findings (NOT personal worship data) in GitHub.
4. Next engineering branch should prioritize device/emulator persistence and populated Room migration checks plus safe encrypted export/restore before making this the only ledger; then pursue Qaza and fasting. Continue source checks for expanded Masnoon content.

Research: [Masnoon source register](research/masnoon-source-register.md) · [Owner requirements](requirements/masnoon-adhkar-munajat-2026-10-owner-brief.md) · [ADR-0019](decisions/0019-source-linked-daily-masnoon-adhkar.md).
