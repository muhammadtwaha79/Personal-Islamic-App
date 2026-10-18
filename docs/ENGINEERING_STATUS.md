# Engineering Status — Ibadah Companion

**Snapshot date 2026-10-18.** This is a GitHub handoff snapshot, not a substitute for inspecting the latest branch/PR/CI. Agent instructions: [CLAUDE.md](../CLAUDE.md); handoff: [AGENT_HANDOFF](AGENT_HANDOFF.md).

## Integrated baseline

- Phase 0 PR #1, Android foundation PR #2, Fard/Nafl/Quran tracking PR #3, docs PR #4, Salawat/Dhikr PR #5 and docs PR #6 were **merged to main**.
- Prior verified complete Salawat debug APK from [Android run 39108600987](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39108600987), final Salawat feature head `5e29b81`, merged as `5edc84e`.
- Repository on `main` before this feature has Room **v4**, offline five Fard daily tracking (Jamaat, Takbeer, challenge 20 Oct–28 Nov 2026), Sunnah/Nafl, Quran half-para/Yaseen/Waqiah/Mulk, Ramadan five-khatam planner, 1200 daily short Salawat, 1200 Istighfar, 1000 Bismillah, 20 Salawat after each Farz, and Dala'il daily manzil.

## Current task — PR #7

- Branch `feature/daily-masnoon-adhkar-sunnah-munajat`; [PR #7](https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/7) OPEN at time of writing. Recheck live GitHub state before any merge.
- **[IMPLEMENTED ON BRANCH]** Source-linked catalog: **28** morning/evening/occasion/post-Salah Dua/Adhkar entries plus **10** context-dependent Sunnah etiquette habits; independent MORNING and EVENING counters; after-Fard scopes independent of existing 1200x Salawat/Istighfar targets.
- Includes **Sayyidul Istighfar**, three Qul Surahs each 3x both morning and evening (chapter titles only—read full Surahs from Mushaf), 3x Bismillahilladhi, 100x Subhanallah wa bihamdihi, Allahumma bika asbahna/amsayna, al-'afw wal-'afiyah, 3x Allahumma afini fi badani, selected daily-life duas, post-Fard Dhikr and Sunnah habit checkbox list.
- **[SOURCE CAUTION]** Requested 7x Hasbiyallahu morning/evening: cited Abu Dawud 5081 is graded `Mawdu'` (fabricated) by al-Albani on the reviewed Sunnah.com page. Entry is OPTIONAL with visible warning and excluded from core completion; no promised protection asserted.
- **[IMPLEMENTED ON BRANCH]** Separate *Munajat-e-Maqbool* daily manzil 1–7 manual selection + optional edition/undo, **without any printed book text**. Existing Dala'il tracker remains separate.
- **[IMPLEMENTED ON BRANCH]** Room **v5**, explicit non-destructive v4→5 migration creating only `daily_sunnah_check` and `munajat_manzil` tables, while continuing to use existing signed recitation event journal. New JVM catalog/source/repetition tests.
- **[CI PENDING FINAL-HEAD]** First code PR build run [39110939138](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39110939138) was cancelled when later commits pushed (Actions concurrency). The **latest** branch commit needs successful full Android compilation, tests, lint, debug APK upload before merging. Do not extrapolate success from the old green Salawat run.

## Unverified work and limitations

- Physical Android installation/launch, offline persistence under real interaction, and migration from populated Room v1–4 databases have NOT been verified.
- No encrypted backup/export exists; Android backup is disabled. **Clearing app data/uninstalling can erase all private worship records.**
- Arabic orthography and hadith grading beyond named source references still require qualified editorial/scholarly review. A source reference does not prove this app's transcription is perfect.
- The 28+10 collection is deliberately **not exhaustive** and the tracker is not a fatwa or evaluation of divine acceptance.
- Qaza Ledger, fasting, detailed custom Duas, optional reminders and better accessibility/localization remain outstanding priorities. Do not claim the whole project completed.

## Next exact steps

1. Get PR #7 latest head SHA and the exact corresponding GitHub Actions run, investigate failures, obtain green compile/test/lint and uploaded debug APK.
2. Under standing owner authorization, mark PR ready and merge only if current head is mergeable and CI is green; verify merge SHA.
3. Update handoff/status docs in a small postmerge docs PR if necessary, green-check and merge.
4. User installs the final debug APK and tests real-device feature flow (without uninstalling if an earlier version has data). Add proper Room migration/device and encrypted export verification soon after; never claim on-device success until tested.

Evidence / research: [Masnoon source register](research/masnoon-source-register.md) · [ADR-0019](decisions/0019-source-linked-daily-masnoon-adhkar.md) · [Owner brief](requirements/masnoon-adhkar-munajat-2026-10-owner-brief.md).
