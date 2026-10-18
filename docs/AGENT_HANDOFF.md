# AI Engineering Handoff — Ibadah Companion

**Written 2026-10-18. GitHub, not any chat, is authoritative.** Next agent must verify main HEAD, current PRs, CI and files before acting. Read [CLAUDE.md](../CLAUDE.md), [ENGINEERING_STATUS.md](ENGINEERING_STATUS.md), [SESSION_LOG.md](SESSION_LOG.md).

## Identity / environment

- Repo: https://github.com/muhammadtwaha79/Personal-Islamic-App (public).
- Application `com.muhammadtwaha.ibadahcompanion`, native Android Kotlin/Compose/Material3/Hilt/Room 2.8.5, AGP 9.4, Gradle 9.6, minSdk26, offline-first/no INTERNET permission.
- No personal prayer journals or other sensitive user data should enter GitHub.
- Owner authorizes agent-managed reviewable PR creation and merge **after green CI**. Never force-push or bypass checks.

## Verified merged phases

PRs #1–4 were merged. On main: Salah/Jamaat/Takbeer, 40-day personal challenge (2026-10-20 through 2026-11-28), Sunnah/Nafl, Quran half-para/Yaseen/Waqiah/Mulk and user-confirmed Ramadan Khatam logs. Physical-device testing remains unverified.

## Active work

- **[MERGED]** Salawat and daily Dhikr PR #5: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/5, merge SHA `5edc84e4d77612b32535e126cfd026b4d312d45c`. No active source branch ownership remains; create a new branch from the latest `main` for future work.
- User-supplied Arabic 20-salawat catalog: `domain/dhikr/SalawatCatalog.kt`. Formulations, historical attribution and promised virtues are **UNVERIFIED**; there is no scholarly stamp. A separate primary daily short Salawat is configured for 1,200x.
- Also 1,200x daily Istighfar, 1,000x Bismillah, 10x of each of 20 user-supplied Salawat after EACH of five Fard prayers, separately tracked. Dala'il daily manzil checkbox with manual 1–7 and optional edition; no copyrighted book text.
- Room v4 with additive 3→4 migration and event journal, UI Dhikr tab, Kotlin counting tests.
- **[VERIFIED]** Final PR #5 source `5e29b81` passed [Android Actions run 39108600987](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39108600987). APK artifact `ibadah-companion-debug-apk` ID `12128819307`. Compilation, JVM tests, lint and artifact upload succeeded. GitHub confirmed merge.

## Next exact action

1. Read GitHub `main` and open PRs (this docs-only postmerge PR may still be open and must be verified).
2. The user should install the [final Salawat debug APK](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39108600987) and verify prior local Salah/Quran data survives the app upgrade, and that Dhikr counters persist after restart.
3. Start a new task branch from latest `main` for Room populated migration tests, local export/restore and other data-safety hardening. Do **not** claim phone verification from a green CI run.
4. Continue Qaza ledger, fasting and sourced morning/evening Adhkar/Duas in dedicated later milestones, with appropriate fiqh/textual review.

## Risks

- No true device/emulator migration validation; Room annotations and CI compile alone are not a proof that migration works.
- No encrypted export/restore; removing app can erase local worship history.
- No authoritative source authentication for the 20 formulas; names or theology in user-supplied text must not be endorsed by the model or code without appropriate review.
- Avoid counting separate after-Fard Salawat into the 1,200x daily short Salawat automatically.
- Do not assume an edition-specific Dala'il manzil schedule.
- Preserve future modules and previous phase decisions in [Roadmap](ROADMAP.md).
