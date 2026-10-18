# Engineering Status

*Current verified project state only. Next-agent instructions are in [AGENT_HANDOFF.md](AGENT_HANDOFF.md); history is in [SESSION_LOG.md](SESSION_LOG.md); rules are in [../CLAUDE.md](../CLAUDE.md). Statements are labelled `PLANNED`, `IMPLEMENTED`, `VERIFIED` or `BLOCKED`.*

## Current phase

**Phase 1: Native Android Foundation + GitHub Actions + First Compiled APK.** Started 2026-10-18 on branch `phase-1/foundation` (owner instructed the resume in the session brief). Phase 0 is complete and merged. **No Android code exists yet.** Phase details: [ROADMAP.md](ROADMAP.md), [phases/phase-1-plan.md](phases/phase-1-plan.md).

## Repository state (as read from GitHub on 2026-10-18)

- `main`: `c022becda506c85f9b2c36aacba7bb2d29d7cff3`, the merge commit of PR #1 (2026-10-18T04:42:19Z, merged by the owner). Unprotected. `VERIFIED`
- `phase-1/foundation`: cut from `c022bec`, was identical to `main` until this session's documentation commits (`b4fd296`, `244ea46`, then the handoff commit that follows them). Read the exact head from GitHub. `VERIFIED`
- `phase-0/foundation`: head `4413d9d`, merged via PR #1, no longer needed. `VERIFIED`
- `test/claude-github-connection`: connectivity test branch; do not merge. `VERIFIED`
- Open pull requests: **none**. PR #1 is merged. (The list-PR tool reports `merged: false` for it, but `merged_at` is set and `main` contains its merge commit; the tool field is unreliable.) `VERIFIED`

## CI

No workflow exists. No build, test or lint has ever run. Last CI run: **none**. No APK exists.

## What exists

- `IMPLEMENTED` and `VERIFIED` by read-back: the Phase 0 documentation set (merged to `main`).
- `IMPLEMENTED` and `VERIFIED` by read-back on `phase-1/foundation`: [architecture/toolchain-matrix.md](architecture/toolchain-matrix.md) (**partial**: AGP 9.4.0, Gradle 9.6.0, JDK 17, Build Tools 36.0.0 verified from official sources; Kotlin, KSP, Compose, Room, Hilt, Navigation, runner image still `UNKNOWN`) and [requirements/salah-takbeer-ula-challenge-owner-brief.md](requirements/salah-takbeer-ula-challenge-owner-brief.md) (owner requirements captured, not yet architected).
- **No Android code, database, workflow, test, app or APK.** Phases 2-9: `PLANNED`.

## Decisions

All accepted architecture decisions are in [decisions/README.md](decisions/README.md) (ADR-0001 to ADR-0015). New decision pending: AGP 9 with built-in Kotlin, no opt-out (recorded in the toolchain matrix; needs an ADR when the matrix is frozen).

## Not verified

Repository Actions settings (owner must confirm; the agent cannot inspect them); Kotlin/KSP/Compose/Room/Hilt/Navigation versions and their compatibility with AGP 9.4; compileSdk and runner image packages; whether the connector can upload binaries; Roman Urdu locale handling; and every religious rule (all `UNVERIFIED` in [fiqh/rule-register.md](fiqh/rule-register.md)).

## Known limitations

- Tools discovered in the 2026-10-18 session: file, branch, commit, pull-request and check-run tools. **No** tool to list workflow runs, read job logs or download artifacts. Another account may differ; re-discover each session.
- The agent sandbox cannot reach Gradle or Google Maven hosts, so **no local build is possible**; all build evidence must come from GitHub Actions check runs.
- The agent cannot download or run the APK; the owner does.
- No branch protection on `main` (owner action recommended; not changed by agents).

## Open questions

- Religious (need a qualified reviewer, all open): see [fiqh/rule-register.md](fiqh/rule-register.md) and section 9 of the Salah requirements capture (Friday/Jumu'ah, Takbeer-e-Ula definition options). None block Phase 1.
- Product/design for Phase 2 (Q2 excused days, Q3 occurrence date rule, Q4 state-model reconciliation): section 9 of [requirements/salah-takbeer-ula-challenge-owner-brief.md](requirements/salah-takbeer-ula-challenge-owner-brief.md).
- Design: open item O1 in [domain/qaza-accounting.md](domain/qaza-accounting.md), needed before Phase 4.
- Architecture risks: [architecture/risk-register.md](architecture/risk-register.md).

## Next task

See "Next exact action" in [AGENT_HANDOFF.md](AGENT_HANDOFF.md).
