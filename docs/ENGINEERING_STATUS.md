# Engineering Status — Ibadah Companion

**Snapshot: 2026-10-19.** Verify live GitHub refs and Actions; this file is a saved handoff. [CLAUDE.md](../CLAUDE.md) · [Agent handoff](AGENT_HANDOFF.md).

## Merged milestones

- **[MERGED] PR #9 — Premium illustrated home and worship UX:** https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/9 ; GitHub merge commit **`7ede15d1dc289ab17ccffea1454860e9444b5c35`**.
- Source PR head **`de71c22a73b0059a7511b7aa150202dbd4466bf8`** passed [Android Actions run **39205889211**](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39205889211): wrapper validation, Kotlin compilation, JVM unit tests, lint, debug APK and artifact upload all **success**. APK artifact `ibadah-companion-debug-apk`, ID **12165339320** (ZIP archive size 12,263,161 bytes). APK itself has not been installed/tested on device after redesign.
- Previous PRs #1–8 were merged, covering Android baseline, Salah, Quran, Sunnah/Nafl, Salawat/Dhikr and previous Masnoon feature. Owner physically installed the **older** APK and reported unsatisfactory UI.

## Current merged app UI

- **0.2.0-home-redesign** Android build, application ID unchanged `com.muhammadtwaha.ibadahcompanion`, minSdk 26.
- Default route = premium Home with actual today Fard progress hero and six original vector-picture illustrated tiles: Daily Salah, Al-Quran, Sunnah & Nafl, Salawat & Dhikr, 40-Day Journey and Manzils. Four-item main bottom navigation (Home, Salah, Quran, Dhikr).
- Five Fard Salah cards plus deliberate **choose → confirm** modal for Jamaat/opening Takbeer, late, uncertain, individual, missed/unknown. Friday Jumu'ah/Dhuhr requires explicit user choice. Existing transactional Room revisions unchanged.
- More legible Nawafil rakah/confirmation, Quran half-para + Surah + five-Ramadan-khatam tracker, 40-day challenge progress and Dhikr counters/Arabic reader.
- New Manzils screen keeps Dala'il al-Khayrat and Munajat-e-Maqbool independently selectable, with edition notes/undo and no book text.
- **Masnoon Duas removed by owner request:** no Masnoon navigation/catalog/screen/repository; legacy Room v5 data tables and schemas remain for non-destructive user-data preservation. Prior source research docs remain historical only.

## Known limitations

- [PENDING] Real-phone visual verification after redesign; screenshot of Home, Salah modal, module navigation, light/dark, compact layout. CI does not prove these.
- [RISK] Debug-signing keys on GitHub ephemeral runners can change. APK upgrades onto an older installed differently-signed app may fail. Do NOT advise uninstall/clear-data if existing records matter; durable, secret-backed signing or verified export/restore is required for data-safe upgrades.
- Room database is local and unencrypted; Android auto-backup disabled, encrypted export not built, and populated migration/device tests incomplete.
- Qaza obligations ledger, fasting/Hijri, accessibility review and source-sensitive Islamic review remain incomplete. Never claim original product roadmap is fully done.

## Exact next action

1. Confirm documentation-sync PR #10, if created, is green and merged. Always use exact head SHA.
2. Provide [redesigned APK run 39205889211](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39205889211) to owner for a carefully planned phone install (signature conflict warning).
3. Request UI screenshots/feedback after safe installation; iterate screen density and controls.
4. Implement stable release signing, encrypted export/restore and verified persistence/migration tests before relying solely on device records; then resume Qaza and fasting program.

[ADR-0020](decisions/0020-home-first-visual-redesign-and-masnoon-removal.md) · [Roadmap](ROADMAP.md).
