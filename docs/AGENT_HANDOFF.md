# Agent Handoff

*Exact instructions for the next Claude agent. Follow the startup protocol in [../CLAUDE.md](../CLAUDE.md) first and **verify GitHub before trusting this file**. Every statement carries a label: `PLANNED`, `IMPLEMENTED`, `VERIFIED`, `BLOCKED`.*

Last updated: 2026-10-18 (see [SESSION_LOG.md](SESSION_LOG.md), entry S-003).

## A. Project identity

- Name: **Ibadah Companion**. Repository: https://github.com/muhammadtwaha79/Personal-Islamic-App (public). Owner/GitHub user: `muhammadtwaha79`.
- Android application ID (working): `com.muhammadtwaha.ibadahcompanion`. `minSdk = 26` ([ADR-0014](decisions/0014-minimum-sdk-api-26.md)).
- Stack (`PLANNED`, none built): Kotlin, Jetpack Compose, Material 3, Room/SQLite, Hilt, Coroutines/Flow, Navigation Compose, DataStore, WorkManager, Gradle Kotlin DSL, GitHub Actions.
- Principles: offline-first, no INTERNET permission, append-only Qaza ledger, religious rules `UNVERIFIED` until reviewed, privacy first. See [../README.md](../README.md), [PROJECT_BRIEF.md](PROJECT_BRIEF.md), [architecture/overview.md](architecture/overview.md).

## B. Current engineering state

| Item | Value |
| --- | --- |
| Phase | 0: Product Architecture and Engineering Foundation |
| Milestone | Phase 0 PR awaiting owner review/merge |
| Current task | **None active.** Waiting for owner approval to merge PR #1. Phase 1 not started. |
| Active development branch | `phase-0/foundation` |
| Open pull request | #1 https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/1 |
| Last verified commit | `b7e525dcf1866eb32018a8ee7e942f03bbf288f5` (content); the branch head may be later by handoff-only commits |
| Last verified CI workflow | **none** (no workflow exists) |

## C. Work completed

- `IMPLEMENTED` + `VERIFIED` (read back from GitHub): Phase 0 documentation set listed in [ENGINEERING_STATUS.md](ENGINEERING_STATUS.md); multi-account continuity system (this file, session log, protocol in CLAUDE.md).
- `VERIFIED`: GitHub connector read and write access on the Phase 0 account (branch, commit and PR operations succeeded).
- Tests executed: **none** (no code exists).
- Engineering decisions: [decisions/README.md](decisions/README.md) (ADR-0001 to ADR-0015). Risks: [architecture/risk-register.md](architecture/risk-register.md).
- Implemented functionality: **none**; no Android code, no APK.

## D. Incomplete work

- Partially implemented functionality: none.
- Uncommitted changes known: none.
- Failed tests / build errors: none (nothing has been built).
- Known defects: none known in code. Documentation gaps: toolchain matrix not yet written; PR mergeable state was `unknown` when last read (GitHub recomputes after pushes).

## E. Next exact action

**Gate (`BLOCKED` on owner):** do nothing beyond read-only verification until the owner says either "merge done, start Phase 1" or "start Phase 1 on this branch". Agents never merge PR #1.

**Once authorized: Phase 1 step 1, the toolchain compatibility matrix.**

- Objective: record, from official sources, a mutually compatible set of Gradle, Android Gradle Plugin, Kotlin, KSP, Compose compiler plugin, Hilt, JDK, compileSdk/targetSdk, and the runner image's SDK packages, with `minSdk = 26`. No versions from memory.
- Relevant files: create `docs/architecture/toolchain-matrix.md` (links and retrieval date per entry); context in [phases/phase-1-plan.md](phases/phase-1-plan.md) and [architecture/ci-build-strategy.md](architecture/ci-build-strategy.md).
- Dependencies: owner confirms the Actions pre-flight settings in the Phase 1 plan (agents cannot inspect them); new branch `phase-1/foundation` cut from the then-current integration head.
- Acceptance: every version has an official source link and retrieval date; cross-compatibility is argued from those sources; unknowns are marked, not guessed.
- Verification: documentation commit read back from GitHub. No CI job applies yet.

## F. Blockers

- `BLOCKED` owner decision: merge of PR #1 and authorization to start Phase 1.
- `BLOCKED` unverified: repository Actions settings (owner must confirm per Phase 1 plan).
- Missing tools (as seen on the Phase 0 account): workflow runs, job logs, artifacts. Re-discover on each account; if absent, use check runs and give the run URL when blocked.
- Missing decisions: religious questions in the [rule register](fiqh/rule-register.md), all `UNVERIFIED` and none blocking Phase 1; open item O1 in [domain/qaza-accounting.md](domain/qaza-accounting.md) before Phase 4; no qualified fiqh reviewer nominated yet.
- Security: repository is public (no data, secrets or personal information may be committed); `main` has no branch protection (owner action recommended: require pull requests, block force pushes; agents must not change it).

## G. Safety conditions

- Do not change without owner approval: accepted ADRs, ledger invariants (L1-L12), the no-INTERNET baseline, religious rule statuses, repository settings, branch protection.
- Owner approval required for: merging, adding any permission, adding analytics/ads/network, changing religious behavior, anything irreversible.
- Data-integrity risks: [architecture/risk-register.md](architecture/risk-register.md) R1-R4 (ledger bypass, migrations, erase/restore, estimate lowering).
- Branch restrictions: never push to `main`; never force-push; follow the safe write procedure in [../CLAUDE.md](../CLAUDE.md).

### Branch ownership (update on every takeover or release)

| Branch | Owner | Purpose | State |
| --- | --- | --- | --- |
| `main` | owner (merges only) | integration | `512e59d`, unprotected |
| `phase-0/foundation` | unlabelled Claude account used in S-001 to S-003 | Phase 0 docs, PR #1 | **released** after S-003; next writer must record takeover in the session log |
| `test/claude-github-connection` | none | connectivity test | abandoned; do not merge; owner may delete in the GitHub UI |

## H. Evidence

- PR #1: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/1
- Last verified content commit (continuity system): https://github.com/muhammadtwaha79/Personal-Islamic-App/commit/b7e525dcf1866eb32018a8ee7e942f03bbf288f5 (parent `08c5883`, verified linear history)
- Earlier key commits on `phase-0/foundation`:
  - https://github.com/muhammadtwaha79/Personal-Islamic-App/commit/08c588364cea67998336f4a8feb750b393fffe61 (documentation fixes)
  - https://github.com/muhammadtwaha79/Personal-Islamic-App/commit/83fe87947bb741f1eadcb4965e9eb3fc0e16045f (owner decisions incorporated)
- Connectivity test commit (test branch): https://github.com/muhammadtwaha79/Personal-Islamic-App/commit/52808d99f3131c0d03401e08b3c46210b3255439
- CI workflow URLs: none exist. GitHub issues: none known (not checked).
