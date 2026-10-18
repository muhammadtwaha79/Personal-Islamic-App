# Agent Handoff — Ibadah Companion

**Date of snapshot: 2026-10-18.** Start by reading [CLAUDE.md](../CLAUDE.md) and [ENGINEERING_STATUS.md](ENGINEERING_STATUS.md), then inspect the **real** GitHub main HEAD, open PRs, branches and latest CI run. Accounts share no chat history: repository is the single source of truth.

## Project identity

- Repository: https://github.com/muhammadtwaha79/Personal-Islamic-App (public).
- Android ID: `com.muhammadtwaha.ibadahcompanion`; minimum SDK 26.
- Technology: Kotlin, Jetpack Compose, Material 3, Room 2.8, Hilt, Navigation Compose, AGP 9.4 / Gradle 9.6 / JDK 17.
- Privacy baseline: fully offline, no INTERNET permission, no analytics, no personal data in GitHub.

## Verified integrated state

- [MERGED] Phase 0 PR #1.
- [MERGED] Phase 1 PR #2, merge `1701350`.
- [MERGED] Phase 2 feature PR #3, merge **`ca2f596dbca76edbf0aa1c603e60b674922947a9`**.
- [GREEN CI] Final Phase 2 PR head `ca2534d`, [workflow run 39103550819](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39103550819): Kotlin/Android build, unit tests, lint, debug APK artifact upload succeeded. Artifact `ibadah-companion-debug-apk` ID `12127319015`. **On-device use not yet verified**.
- [CURRENT DOCUMENTATION BRANCH] `docs/postmerge-worship-status`; at time of writing this branch is updating stale README/status/handoff and has not necessarily been merged. Check live PR state and complete it when green, if still pending.
- [BRANCH OWNERSHIP] No active code development agent currently owns `phase-2/worship-tracking`; it was merged and released. Owner permits verified PR merges (no direct force pushes).

## What is implemented

Fard Salah user logs with Jamaat and Takbeer statuses, 40-day Oct20 challenge, Sunnah/Nafl daily logs (Tahajjud, Ishraq, Chasht, Awwabeen, pre-Asr and pre-Isha 4), Quran daily half-para/Yaseen/Waqiah/Mulk, Ramadan reading volume target and **separate** confirmed full khatam events. All local Room persisted (v3; additive migrations from v1). See [Owner brief](requirements/2026-10-worship-and-quran-owner-brief.md) and [ADR-0017](decisions/0017-worship-quran-tracking-slices.md).

## Exact next engineering action

1. Verify documentation PR is merged; if not, inspect its CI and merge it once green per owner authorization.
2. Install debug APK on a physical Android device. Test recording Salah, optional prayers, Quran records, app restart and historical corrections; the owner needs to report device findings.
3. **Before trusting this as sole personal record**, add Android instrumented integration tests for Room migrations 1→2→3 and UI persistence; prioritize safe encrypted export/restore (no backup exists yet).
4. Continue missing Qaza ledger (critical pre-existing owner goal), fasting/Hijri integration, and the newly requested Adhkar/Duas module. Keep work on separate task branches with small reviewable PRs.
5. Religious source-sensitive items remain UNVERIFIED and must be reviewed by a qualified scholar; do not bake disputed rules into automated validity checks.

## Safety / constraints

- No device database, worship journal, keystore, secret, backup or OAuth credential in public repo.
- No `INTERNET` permission without a new security ADR and owner approval.
- No force pushes or silent history replacement. Never mark a verified-device status based on CI alone.
- The user requested 20 Oct 2026 start and 40-day challenge through 28 Nov inclusive. No fabricated historical data.
- Multi-Claude-account continuity: update this file, [SESSION_LOG](SESSION_LOG.md), [ENGINEERING_STATUS](ENGINEERING_STATUS.md) and [ROADMAP](ROADMAP.md) after major steps and re-verify GitHub facts.

## Links

[PR #3](https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/3) · [CI](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39103550819) · [Roadmap](ROADMAP.md) · [Fiqh register](fiqh/rule-register.md).
