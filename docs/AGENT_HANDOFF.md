# Agent Handoff — Ibadah Companion

**Last written 2026-10-18.** This may become stale when a PR is merged. The NEXT agent MUST verify GitHub reality before trusting these notes. Read [CLAUDE.md](../CLAUDE.md), [ENGINEERING_STATUS](ENGINEERING_STATUS.md) and [SESSION_LOG](SESSION_LOG.md).

## Identity

Repository: https://github.com/muhammadtwaha79/Personal-Islamic-App; application ID `com.muhammadtwaha.ibadahcompanion`, Android minSdk 26. Native Kotlin / Jetpack Compose / Material 3 / Hilt / Room / Gradle 9.6 / AGP 9.4, offline-first and no INTERNET permission. All personal worship records are sensitive and stored on the device, **never** in GitHub.

## Git history and branch ownership

- `main`: Phase 0 and Phase 1 are merged. Phase 1 PR #2 merged as `1701350`.
- `phase-2/worship-tracking`: ChatGPT-authored Phase 2 MVP and Quran expansion, open [PR #3](https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/3) at this file's time of writing.
- The owner explicitly authorized the engineering agent to merge PRs autonomously when verified, on 2026-10-18. Never force-push or overwrite.
- Branch ownership: ChatGPT currently holds `phase-2/worship-tracking`; release after merge. Subsequent agents should create new task branches from verified `main`.
- Inspect actual PR and branch SHA! Do not mistake old 2026-10-18 Phase 1 context for current state.

## Verified technical milestones

- Phase 1 successful run: https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39101278554 .
- Phase 2 implementation successful compile/test/lint/assemble/upload job on implementation SHA `76dd7a2`: https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39102929249 .
- **Final PR #3 head may be newer** due challenge streak refinement, extra Nafl types, ADR and memory commits. Obtain the latest workflow result and artifact for its actual head; do not use an older green check to justify merging a newer commit.
- There has been **no verified emulator/device test or migration upgrade from a populated v1 database**; these remain gating risks before depending on the tracker as a sole source.

## Implemented scope and limitations

Four functional routes: five Fard Salah (Jamaat vs alone, opening Takbeer, explicit missed, historical dates and edits), 40-day fixed October 20 challenge, Sunnah/Nafl voluntary tracking, Quran half-juz+Yaseen/Waqiah/Mulk daily and manually-dated Ramadan five-khatam tracking with separate volume / full completion counts. The database is at schema **v3**, with **two** explicit additive migrations, `1→2` and `2→3`. Android migration upgrade tests remain pending. No Qaza ledger, fasting, Azkar/Duas, prayer times, fiqh-certified rules or encrypted backups yet.

**Owner 2026-10-20 start:** Do not fabricate historical data. The 40-day challenge is 20 Oct through 28 Nov 2026 inclusive. User can backfill later, and future record actions are disabled. Default daily Quran target is half-para; Ramadan goal is 5 manually confirmed complete khatams, with 150 juz volume as planning metric, not proof.

## Next precise action

1. Re-read current PR #3 and CI runs; if final commit green and APK uploaded, merge PR #3 into `main` and verify.
2. If necessary create a tiny postmerge branch to correct stale handoff references; merge with CI.
3. Tell owner how to download/install APK. Clearly distinguish CI pass from device-tested stability.
4. For next development branch, prioritize reliable Room migration/instrumented testing and safe encrypted export, plus outstanding Qaza ledger and fasting. Adhkar/Duas is a separately approved future module in [requirements](requirements/2026-10-worship-and-quran-owner-brief.md).
5. Keep [Roadmap](ROADMAP.md), [ADR-0017](decisions/0017-worship-quran-tracking-slices.md), fiqh register and risks consistent. Never silently assign unverifed religious rulings.

## Tool limitations / hygiene

This account has GitHub branch, PR, Actions run/job/artifact tools; future Claude accounts must rediscover their capabilities. Do not reveal credentials. Work on one branch with one writer at a time; track commits and update status at handoffs.
