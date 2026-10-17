# CLAUDE.md - Permanent Engineering Instructions

This file is the standing instruction set for every AI-assisted session on **Ibadah Companion**. A future session has no memory of earlier chats. **The repository is the memory.**

## 1. Resuming work (do this first, every session)

1. Read `docs/ENGINEERING_STATUS.md` (current phase, last verified commit, next task).
2. Read `docs/ROADMAP.md` for the active phase's acceptance criteria.
3. Inspect the actual repository state with the GitHub tools. Do not trust this file or the status file over what is really on GitHub; if they disagree, report the discrepancy.
4. Discover which tools are actually available. Never pretend a tool exists.

## 2. Project in one paragraph

A private, offline-first native Android app (Kotlin, Compose, Material 3, Room, Hilt, Coroutines/Flow, WorkManager, DataStore) for Salah, Sunnah/Nafl, Qaza recovery and fasting. One user. No account, ads, analytics or required network. See `docs/PROJECT_BRIEF.md`.

## 3. Non-negotiable rules

**Honesty about verification.** Never claim: tests passed unless they ran; an APK exists unless CI produced it; data is persisted unless persistence is implemented; encryption exists unless implemented; a GitHub operation succeeded unless a tool result confirms it. Distinguish *planned* from *implemented* from *verified*.

**Religious accuracy.** The app is a tracking tool, not a mufti. Never fabricate Quran or Hadith references, scholarly consensus, or rulings. Every religious rule lives in `docs/fiqh/rule-register.md` with a verification status. New or changed rules start as `UNVERIFIED`. Do not silently resolve disputed questions; make them configurable or escalate.

**Data integrity.** The Qaza ledger is append-only (`docs/domain/qaza-accounting.md`). Never replace it with an editable counter. Never convert an estimate into fabricated prayer dates. No ledger semantic change without an ADR and migration tests.

**Privacy.** The repository is public. Never commit worship data, database files, backups, exports, keystores or secrets. Do not add the INTERNET permission, analytics, ads or crash reporters without an ADR and explicit approval.

**No fake functionality.** No buttons that simulate writes. No placeholder screens presented as features.

## 4. Git workflow

- Never push to or merge into `main` without explicit authorization from the owner.
- Branch names: `phase-N/<topic>`, `feat/<topic>`, `fix/<topic>`, `docs/<topic>`.
- Small, reviewable commits. Message format: `type(scope): summary` (types: feat, fix, docs, test, build, ci, refactor, chore).
- Never force-push. Never delete existing work. Do not change repository visibility or security settings.
- After every write, read the result back from GitHub before reporting success.
- Open a pull request when a milestone is ready for review.

## 5. Escalate to the owner before deciding

Religious correctness; irreversible data loss; security-sensitive operations; repository permissions; production publication; core product identity changes.

Make other reasonable, reversible engineering decisions independently and document the assumption (ADR or status file).

## 6. Known tooling limits (verify each session)

- The GitHub connector can read/write files, branches, commits and pull requests. Last audit found **no tools to list workflow runs, read job logs or download artifacts**. CI visibility is through pull-request check runs only; see `docs/architecture/ci-build-strategy.md` for the mitigation.
- Binary files (for example `gradle-wrapper.jar`) must not be assumed uploadable through the connector. See the wrapper bootstrap plan in the CI strategy.
- Do not recommend Claude Code, local Android Studio, or manual ZIP uploads; the workflow is Claude Chat -> GitHub connector -> GitHub Actions.

## 7. Engineering standards

Correctness, testability, security, accessibility, performance and data integrity all count. Domain logic is pure Kotlin with no Android dependencies. Every ledger and fiqh-rule behavior has tests. Do not weaken CI checks to turn a build green. Do not invent dependency versions; verify against authoritative sources.

## 8. Definition of done (any task)

Code or docs committed on a branch; result verified on GitHub; tests added or updated and actually run in CI (for code phases); `docs/ENGINEERING_STATUS.md` updated; open religious or security questions recorded.
