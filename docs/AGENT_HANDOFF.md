# Agent Handoff — Ibadah Companion

**Snapshot 2026-10-19.** GitHub is the source of truth across ChatGPT and Claude Chat accounts. Read [CLAUDE.md](../CLAUDE.md), [ENGINEERING_STATUS.md](ENGINEERING_STATUS.md), [SESSION_LOG.md](SESSION_LOG.md), then verify current main HEAD, open PRs and Actions.

## Repository and verified release

Public repo: https://github.com/muhammadtwaha79/Personal-Islamic-App. Native Kotlin/Compose/Material3/Hilt/Room schema v5, AGP9.4/Gradle9.6, Android minSdk 26, offline-first and **no INTERNET permission**.

**[MERGED] Premium UI PR #9**: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/9, merge SHA `7ede15d1dc289ab17ccffea1454860e9444b5c35`.

**[VERIFIED CI]** PR head `de71c22a73b0059a7511b7aa150202dbd4466bf8` passed [run 39205889211](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39205889211) (compiler, unit tests, lint and uploaded APK). Artifact `ibadah-companion-debug-apk`, ID `12165339320`. Android versionCode 2, versionName `0.2.0-home-redesign`.

## User's newest product decision

The owner physically installed an **older APK** and judged its UI bad. They requested a premium visual design, an illustrated-home-first module opening experience, higher quality Salah and other confirmation controls, and removal of **Masnoon Duas**. PR #9 delivered:
- Home-first 6 Canvas illustrated route cards and same-day Salah hero; 4 core bottom tabs.
- Calm emerald/ivory typography/color/shape system.
- Salah cards and a two-step prayer confirmation dialog, plus refreshed Nafl, Quran, Dhikr and challenge layouts.
- Standalone Manzils route for *Dala'il al-Khayrat* and *Munajat-e-Maqbool*.
- Masnoon Duas screen/catalog/repository and obsolete test **removed from active source**, while **legacy Room entities/tables retained** to protect existing records (no destructive migration). Source research remains historical.

See [ADR-0020](decisions/0020-home-first-visual-redesign-and-masnoon-removal.md) and [UI plan](phases/2026-10-elite-home-redesign-plan.md).

## Next exact actions

1. Inspect any docs-only postmerge PR (#10 expected) and CI, merge if green under standing owner authorization.
2. Owner needs to safely **install and visually evaluate** the redesigned APK from run `39205889211` and send screens (Home, Salah confirm, Quran, Dhikr, Manzils) without revealing private worship content.
3. Existing old installed debug APK may use a **different GitHub Actions debug signer**. If so Android reports installation failure; before any uninstall/clear-data, confirm whether older device records matter and arrange safe export/restore. Stable secret-managed signing/backup is a **high-priority unresolved risk**. Do not commit a signing key to public GitHub.
4. Next engineering milestone should be Android instrumentation/migration tests, secure local export/restore, stable release signing, then Qaza recovery and fasting. Don't reinvent Masnoon Duas without owner request.

## Religious and privacy safeguards

All personal worship history remains on device and must never be uploaded to public GitHub. Existing Salaah/Quran/Nawafil/Dhikr theological assumptions require qualified scholarly review; no invented rewards. Ramadan and manzil schedules remain user-chosen. Missing voluntary goals are not moral verdicts. No device usability acceptance has yet been recorded for the redesign.
