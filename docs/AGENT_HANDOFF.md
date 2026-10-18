# Agent Handoff

*Exact instructions for the next Claude agent. Follow the startup protocol in [../CLAUDE.md](../CLAUDE.md) first and **verify GitHub before trusting this file**. Every statement carries a label: `PLANNED`, `IMPLEMENTED`, `VERIFIED`, `BLOCKED`.*

Last updated: 2026-10-18 (see [SESSION_LOG.md](SESSION_LOG.md), entry S-004).

## A. Project identity

- Name: **Ibadah Companion**. Repository: https://github.com/muhammadtwaha79/Personal-Islamic-App (public). Owner/GitHub user: `muhammadtwaha79`.
- Android application ID (working): `com.muhammadtwaha.ibadahcompanion`. `minSdk = 26` ([ADR-0014](decisions/0014-minimum-sdk-api-26.md)).
- Stack (`PLANNED`, none built): Kotlin, Jetpack Compose, Material 3, Room/SQLite, Hilt, Coroutines/Flow, Navigation Compose, DataStore, WorkManager, Gradle Kotlin DSL, GitHub Actions. Build tooling decision in progress: AGP 9.4.0 + Gradle 9.6.0 (see [architecture/toolchain-matrix.md](architecture/toolchain-matrix.md)).
- Principles: offline-first, no INTERNET permission, append-only Qaza ledger, religious rules `UNVERIFIED` until reviewed, privacy first. See [../README.md](../README.md), [PROJECT_BRIEF.md](PROJECT_BRIEF.md), [architecture/overview.md](architecture/overview.md).

## B. Current engineering state

| Item | Value |
| --- | --- |
| Phase | 1: Native Android Foundation + GitHub Actions + First Compiled APK |
| Milestone | Phase 1 step 1 (toolchain matrix) partially done; no code yet |
| Current task | Resolve the remaining `UNKNOWN` rows of the toolchain matrix from official sources |
| Active development branch | `phase-1/foundation` (cut from `main` `c022bec`) |
| Open pull requests | **none** (PR #1 merged by the owner on 2026-10-18) |
| Last verified commits | `b4fd296` (toolchain matrix), `244ea46` (requirements capture); the head is later by the handoff commit that records them |
| Last verified CI workflow | **none** (no workflow exists) |

## C. Work completed

- Phase 0 documentation set: merged to `main` in `c022bec`. `VERIFIED`
- `IMPLEMENTED` + `VERIFIED` (read back): partial toolchain matrix `b4fd296`; owner Salah/Takbeer-e-Ula requirements capture `244ea46`; this status/handoff/log reconciliation.
- Tests executed: **none** (no code exists).
- Implemented functionality: **none**; no Android code, no APK.

## D. Incomplete work

- Toolchain matrix: only AGP, Gradle (with checksums), JDK and Build Tools are verified. Remaining rows are listed in the matrix.
- Uncommitted changes known: none.
- Failed tests / build errors: none (nothing has been built).
- Known defects: none in code. Documentation: `domain/` Salah models have **not** been reconciled with the owner's new requirements (Q4).

## E. Next exact action

**Continue Phase 1 step 1: finish the toolchain matrix.**

- Objective: from official sources only, establish and record the Kotlin version AGP 9.4 built-in Kotlin works with, a compatible KSP, the Compose compiler plugin pairing, Compose BOM/Material 3/Activity/Lifecycle, Navigation Compose, Room, Hilt/Dagger (AGP 9 compatible), coroutines/test libraries, compileSdk/targetSdk, the GitHub runner image's JDK and Android SDK packages, and the pinned commit SHAs of the GitHub Actions to be used.
- Where to look: AndroidX release pages and Google Maven metadata, Kotlin and KSP release notes, Dagger/Hilt release notes and docs for AGP 9, GitHub runner-images documentation, GitHub release pages for each action. Third-party repositories' `libs.versions.toml` files returned stale AGP 8-era versions and must not be used as evidence.
- Acceptance: every version has an official source link and retrieval date; compatibility is argued from those sources; unknowns remain marked.
- Then: step 2 (text-only project skeleton). The skeleton must not apply `org.jetbrains.kotlin.android` (AGP 9 built-in Kotlin) and must use KSP, not kapt.
- **Gate before step 3 (wrapper bootstrap):** the owner must confirm repository Actions are enabled and workflow permissions are set per the Phase 1 plan.
- After the Phase 1 CI gate passes and the owner approves the merge: Phase 2 planning starts from [requirements/salah-takbeer-ula-challenge-owner-brief.md](requirements/salah-takbeer-ula-challenge-owner-brief.md).

## F. Blockers

- `BLOCKED` owner confirmation of repository Actions settings (needed before step 3; agents cannot inspect them).
- Missing tools in the 2026-10-18 session: workflow runs, job logs, artifacts. Use PR check runs and the CI report comment; if results cannot be read, report `BLOCKED` with the run URL.
- No local build is possible from the agent sandbox (Gradle/Google Maven hosts unreachable).
- Missing decisions: religious questions in the [rule register](fiqh/rule-register.md) and Q1 to Q5 in the Salah requirements capture; open item O1 in [domain/qaza-accounting.md](domain/qaza-accounting.md) before Phase 4; no qualified fiqh reviewer nominated yet.
- Security: repository is public (no data, secrets or personal information may be committed); `main` has no branch protection (owner action recommended; agents must not change it).

## G. Safety conditions

- Do not change without owner approval: accepted ADRs, ledger invariants (L1-L12), the no-INTERNET baseline, religious rule statuses, repository settings, branch protection.
- Owner approval required for: merging, adding any permission, adding analytics/ads/network, changing religious behavior, anything irreversible.
- Data-integrity risks: [architecture/risk-register.md](architecture/risk-register.md) R1-R4.
- Branch restrictions: never push to `main`; never force-push; follow the safe write procedure in [../CLAUDE.md](../CLAUDE.md).

### Branch ownership (update on every takeover or release)

| Branch | Owner | Purpose | State |
| --- | --- | --- | --- |
| `main` | owner (merges only) | integration | `c022bec` (PR #1 merged), unprotected |
| `phase-1/foundation` | unlabelled Claude account used in S-004 (taken over on the owner's instruction in the session brief) | Phase 1 foundation | **held**; the next writer must record takeover in the session log |
| `phase-0/foundation` | none | Phase 0 docs, merged in PR #1 | **released**, no longer needed |
| `test/claude-github-connection` | none | connectivity test | abandoned; do not merge; owner may delete in the GitHub UI |

## H. Evidence

- PR #1 (merged 2026-10-18T04:42:19Z): https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/1 ; merge commit https://github.com/muhammadtwaha79/Personal-Islamic-App/commit/c022becda506c85f9b2c36aacba7bb2d29d7cff3
- Toolchain matrix commit: https://github.com/muhammadtwaha79/Personal-Islamic-App/commit/b4fd2968473357331df9d20ef6a9b52db3b75d3e (parent `c022bec`, verified linear history)
- Requirements capture commit: https://github.com/muhammadtwaha79/Personal-Islamic-App/commit/244ea46f697248f4063590abb9cd7db17ded528f (parent `b4fd296`, verified linear history)
- CI workflow URLs: none exist. GitHub issues: none known (not checked).
