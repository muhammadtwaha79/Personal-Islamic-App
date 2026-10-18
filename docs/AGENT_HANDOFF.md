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

- Working branch: `feature/salawat-adhkar-counting`, PR #5 https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/5.
- User-supplied Arabic 20-salawat catalog: `domain/dhikr/SalawatCatalog.kt`. Formulations, historical attribution and promised virtues are **UNVERIFIED**; there is no scholarly stamp. A separate primary daily short Salawat is configured for 1,200x.
- Also 1,200x daily Istighfar, 1,000x Bismillah, 10x of each of 20 user-supplied Salawat after EACH of five Fard prayers, separately tracked. Dala'il daily manzil checkbox with manual 1–7 and optional edition; no copyrighted book text.
- Room v4 with additive 3→4 migration and event journal, UI Dhikr tab, Kotlin counting tests.
- CI run **39108218410** on source `61ea289` succeeded and uploaded APK, but **later code/performance/doc commits exist**. Re-verify PR's *final* current SHA before merging.

## Next exact action

1. Inspect PR #5 head and Actions run for **same** SHA. If all build/test/lint/APK artifact checks pass, change draft PR to ready and merge per standing user authorization.
2. Read committed feature status and update `docs/ENGINEERING_STATUS.md` if current.
3. Ask user to install new debug APK from successful CI run; confirm previous personal logs survive upgrade (NO DATA WIPE), and a short Salawat +1 / after-Fard +10 / correction / daily manzil persist after restart. Tests on a populated database are still missing.
4. Branch for next milestone from merged main: device migration tests and user-controlled export/backup before relying exclusively on the tracker. Then Qaza ledger, fasting and reviewed daily adhkar/dua library remain important.

## Risks

- No true device/emulator migration validation; Room annotations and CI compile alone are not a proof that migration works.
- No encrypted export/restore; removing app can erase local worship history.
- No authoritative source authentication for the 20 formulas; names or theology in user-supplied text must not be endorsed by the model or code without appropriate review.
- Avoid counting separate after-Fard Salawat into the 1,200x daily short Salawat automatically.
- Do not assume an edition-specific Dala'il manzil schedule.
- Preserve future modules and previous phase decisions in [Roadmap](ROADMAP.md).
