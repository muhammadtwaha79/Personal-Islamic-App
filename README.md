# Ibadah Companion

An **offline-first native Android** personal Islamic worship tracker. Implemented in Kotlin, Jetpack Compose, Material 3, Hilt, Room/SQLite, with GitHub Actions CI.

Application ID: `com.muhammadtwaha.ibadahcompanion` • Minimum Android version: API 26.

## What currently works (first engineering MVP)

- Five daily Fard Salah records: self-reported completion, Jamaat vs individual, joined-late and opening Takbeer-e-Ula status, explicit missed/unknown and historical corrections.
- Personal 40-day Takbeer-e-Ula tracking program: **20 Oct–28 Nov 2026**, five opportunities daily, total 200; counts and streak metrics derived from canonical Salah records.
- Sunnah and Nafl daily checklists with user-recorded rakah counts, including Tahajjud, Ishraq, Chasht/Duha, Awwabeen, four pre-Asr and four pre-Isha Sunnah and other voluntary practices.
- Quran reading log: half-para daily baseline from **20 October 2026**, Surah Yaseen, Waqiah and Mulk independently marked complete.
- Ramadan planner: user-selected Ramadan date range, **five complete Quran khatams** target, 150-juz reading-volume planning, plus **separate user-confirmed full-khatam events**.
- Local Room persistence (schema v3) and non-destructive v1→v2→v3 migration declarations.
- English-language working interface with four tabs; calm Material 3 dark/light theme.

**Important:** Code has passed GitHub Actions compilation, JVM unit tests, lint and debug APK assembly, but **Android phone/emulator use and a database migration on a populated device have not yet been verified**. This is an engineering MVP, not an audited production release.

## Verified build and APK

- [PR #2 — Android foundation, merged](https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/2)
- [PR #3 — Salah, Nawafil and Quran tracker, merged](https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/3)
- [Phase 2 successful GitHub Actions run](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39103550819) (source head `ca2534d`, all jobs successful, debug APK artifact uploaded).

To try it, open the run while signed into GitHub, scroll to **Artifacts**, download `ibadah-companion-debug-apk`, extract the ZIP and install `app-debug.apk` on your Android phone. This is a **debug** APK, not a Play Store release.

**Data-loss warning:** The app uses a local database; an encrypted export/restore system is **not yet implemented** and Android auto-backup is disabled. Uninstalling the application, clearing app data or losing the phone can erase records. Do not rely on it as your sole long-term record until backup/restore is independently verified. Never share religious habit data on GitHub.

## Planned, not yet implemented

- Production-grade append-only **Qaza Salah recovery ledger** with the user's independently confirmed estimates, plus Witr rule review.
- Ramadan/Qaza/voluntary **fasting** with lunar calendar safeguards.
- Dedicated **Adhkar, Dhikr, Salawat and Duas** tracking; sourced religious wording needs qualified review.
- Accurate prayer-time calculations, selected Asr conventions, configurable fiqh profile, travel/excuses, repeatable 40-day challenge attempts, reminder system.
- Encrypted backup, app lock, device UI tests, migration tests, full accessibility/localization and release signing.

**Religious correctness:** All source-sensitive rulings in [fiqh register](docs/fiqh/rule-register.md) remain `UNVERIFIED`. This product is a personal record keeper, not a fatwa or a measure of acceptance by Allah.

## Cross-account AI engineering continuity

GitHub is the authoritative institutional memory. Every new Claude or ChatGPT engineering session should read [CLAUDE.md](CLAUDE.md), then [ENGINEERING_STATUS](docs/ENGINEERING_STATUS.md) and [AGENT_HANDOFF](docs/AGENT_HANDOFF.md), verify the live branch/PR/CI evidence, and avoid starting over or force-pushing.

Architecture/risks: [Roadmap](docs/ROADMAP.md) · [Qaza design](docs/domain/qaza-accounting.md) · [ADR-0017](docs/decisions/0017-worship-quran-tracking-slices.md) · [Security](docs/security/privacy-model.md).

The repository is public. Never commit personal worship history, local database files, backups, secrets or signing keys.
