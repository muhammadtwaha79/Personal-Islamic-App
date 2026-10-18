# Agent Handoff — Ibadah Companion

Read [CLAUDE.md](../CLAUDE.md) before acting. This file is intentionally concise. **GitHub is the source of truth; recheck all SHAs, PRs, and run conclusions.**

## Identity

- Repository: https://github.com/muhammadtwaha79/Personal-Islamic-App (public)
- App: Ibadah Companion; ID `com.muhammadtwaha.ibadahcompanion`; minSdk 26.
- Stack: Kotlin + Compose/Material 3, Room, Hilt, Navigation Compose, AGP 9.4.0, Gradle 9.6.0, JDK 17.
- Privacy: no INTERNET permission, no analytics, no user records in GitHub.
- Religious claims must remain unverified until qualified review; see [fiqh rule register](fiqh/rule-register.md).

## Current state, 2026-10-18

- Phase 0 was merged into `main` in `c022bec`.
- Phase 1 source and CI are implemented on `phase-1/foundation`.
- Draft PR #2: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/2
- Implementation SHA **`00a9ed6ffb3b4d7ab0cb724f7b2e65a7c5cc8932`** has a **successful** Android CI run: https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39100930667
- At that SHA, wrapper validation, unit tests, lint, debug APK assembly, report upload and debug APK artifact upload succeeded.
- Artifact: `ibadah-companion-debug-apk`, ID `12126855738` on run `39100930667`. Owner has not yet verified installation on an Android device.
- More recent documentation commits (ADR-0016, status, handoff, session log) require final-head CI verification.
- No Phase 2 implementation yet. The user's tracking program should support start date 2026-10-20, congregation status, Takbeer-e-Ula and the 40-day challenge; requirements are in [owner brief](requirements/salah-takbeer-ula-challenge-owner-brief.md).

## Exact next action

1. Confirm final `phase-1/foundation` branch HEAD and verify Android CI for that exact SHA; if it fails, read logs, fix without weakening tests, repeat.
2. Ensure CI's APK artifact exists on the final green run; give owner workflow URL and artifact instructions.
3. Change draft PR #2 to ready for review once satisfied (do NOT merge). Owner reviews and authorizes merge.
4. After merge, create a dedicated Phase 2 branch for Fard Salah recording and congregation/Takbeer-e-Ula. First reconcile [domain models](domain/domain-models.md) and the owner brief; document handling of Friday Jumu'ah, excused situations, occurrence dates and exact Takbeer-e-Ula definition, with religious review where needed.

## Branch ownership

| Branch | Owner | State |
| --- | --- | --- |
| `main` | repository owner only | integrate reviewed PRs; no agent direct pushes |
| `phase-1/foundation` | this ChatGPT engineering session | **released once this handoff lands**; future writer must verify HEAD and take over explicitly |
| `phase-0/foundation` | historical | merged, released |
| `test/claude-github-connection` | none | obsolete test branch; do not merge |

## Limits and open questions

- This account's GitHub tools include access to PR-triggered runs, job steps, and workflow artifacts; re-discover tools on each new account.
- App device installation is not verified by CI. No worship tracking functionality exists in the Phase 1 UI.
- All religious references are `UNVERIFIED`; [owner brief section 9](requirements/salah-takbeer-ula-challenge-owner-brief.md) records the Phase 2 open questions.
- Qaza estimate-surplus issue O1 remains open before the Qaza phase.
- See [risk register](architecture/risk-register.md), [CI design](architecture/ci-build-strategy.md), [toolchain matrix](architecture/toolchain-matrix.md), and [ENGINEERING_STATUS.md](ENGINEERING_STATUS.md).

Never rely on a chat transcript, do not force-push, do not merge without owner authorization, and update [SESSION_LOG.md](SESSION_LOG.md) after meaningful work.
