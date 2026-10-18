# CLAUDE.md - Permanent Engineering Instructions

Standing instructions for every AI-assisted session on **Ibadah Companion**. Sessions run on multiple Claude Chat accounts with no shared memory. **GitHub is the single source of truth**: no engineering-critical knowledge may live only in a chat.

## Memory files and their single responsibilities

| File | Responsibility (do not duplicate its content elsewhere; link to it) |
| --- | --- |
| `CLAUDE.md` (this file) | Permanent rules and protocols |
| [`docs/ENGINEERING_STATUS.md`](docs/ENGINEERING_STATUS.md) | Current verified project state |
| [`docs/AGENT_HANDOFF.md`](docs/AGENT_HANDOFF.md) | Exact instructions for the next agent; branch ownership table |
| [`docs/SESSION_LOG.md`](docs/SESSION_LOG.md) | Chronological record of meaningful sessions |
| [`docs/ROADMAP.md`](docs/ROADMAP.md) | Phase objectives, dependencies, acceptance criteria |
| [`docs/decisions/`](docs/decisions/README.md) | ADRs: why decisions were made |
| [`docs/fiqh/rule-register.md`](docs/fiqh/rule-register.md) | The only home of religious rules |

## 1. Mandatory startup protocol (every new session or account)

Do these in order. Keep reading minimal: do **not** read every file to resume a small task.

1. **Tools.** Discover the GitHub tools you actually have in this session (never assume another account's tools) and authenticate (for example read the authenticated user). Note whether you can write and whether any Actions/log/artifact tools exist.
2. **Memory.** Read `docs/ENGINEERING_STATUS.md`, then `docs/AGENT_HANDOFF.md`.
3. **Reality.** Inspect GitHub itself: branches and their heads, the active branch's recent commits, open pull requests, and CI check runs for the head SHA when tools permit.
4. **Compare.** Check the documents against GitHub: head SHA versus the SHA in the handoff, PR state, branch list, claimed CI results. List every discrepancy.
5. **Unexpected end?** If the branch has commits after the handoff's last verified SHA that are not handoff-only, or the handoff is missing or stale, assume the previous session ended unexpectedly. Reconstruct state from commits (changed files), PRs and check runs. Do not assume the previous agent finished its handoff.
6. **Discrepancies.** Report them to the owner before acting. Reconcile by a small documentation commit only when safe; anything touching data integrity, security, permissions or religious rules waits for the owner.
7. **Then** read only what the current task needs: the active roadmap phase, relevant ADRs, relevant architecture/domain docs, and the fiqh register only when touching religious behavior.
8. **Resume** the single next action in the handoff. Do not start implementation from the handoff alone: verify the repository first.

## 2. Project in one paragraph

A private, offline-first native Android app (Kotlin, Compose, Material 3, Room, Hilt, Coroutines/Flow, WorkManager, DataStore) for Salah, Sunnah/Nafl, Qaza recovery and fasting. One user. No account, ads, analytics or required network. See [`docs/PROJECT_BRIEF.md`](docs/PROJECT_BRIEF.md).

## 3. Non-negotiable rules

**Honesty about verification.** Label statements `PLANNED`, `IMPLEMENTED`, `VERIFIED` or `BLOCKED`. Never claim: tests passed unless they ran; an APK exists unless CI produced it; data is persisted unless persistence is implemented; encryption exists unless implemented; a GitHub operation succeeded unless a tool result confirms it. Never claim completion without evidence (commit, PR or run URL).

**CI evidence.** CI success means the authoritative result for the exact head SHA: a check-run conclusion read from GitHub (or Actions run data if your tools provide it). A bot-generated status comment is supplementary and **never sufficient alone**. If you cannot read authoritative results, say `BLOCKED`, give the Actions run URL, and name the blocker.

**Religious accuracy.** The app is a tracking tool, not a mufti. Never fabricate Quran or Hadith references, scholarly consensus, or rulings. Every religious rule lives only in the [rule register](docs/fiqh/rule-register.md) with ID, school, status, sources, reviewer and implementation implications. Never introduce or change a ruling because a previous Claude response said so. New or changed rules start `UNVERIFIED`; verification needs a named qualified reviewer. Changing religious behavior requires register update, version bump and owner approval. Do not silently resolve disputed questions; make them configurable or escalate.

**Data integrity.** The Qaza ledger is append-only ([`docs/domain/qaza-accounting.md`](docs/domain/qaza-accounting.md)). Never replace it with an editable counter. Never convert an estimate into fabricated prayer dates. No ledger semantic change without an ADR and migration tests.

**Privacy and secrets.** The repository is public. Never commit worship data, database files, backups, exports, keystores, tokens, OAuth secrets or credentials. Never write chat transcripts or personal information into docs or logs. Do not add the INTERNET permission, analytics, ads or crash reporters without an ADR and explicit owner approval.

**No fake functionality.** No buttons that simulate writes. No placeholder screens presented as features.

## 4. Git workflow, branch ownership and concurrency

- `main` is the integration branch. **Never push to or merge into `main` without explicit owner authorization.** Merges are done by or approved by the owner.
- Branch names: `phase-N/<topic>`, `feat/<topic>`, `fix/<topic>`, `docs/<topic>`.
- Never force-push. Never delete anyone's work or branch. Do not change repository visibility or security settings (including branch protection) without owner approval.
- Small, reviewable commits: `type(scope): summary`.

**Ownership.** Only one writing agent owns a development branch at a time. Ownership is recorded in the *Branch ownership* table in [`docs/AGENT_HANDOFF.md`](docs/AGENT_HANDOFF.md). Take over a branch only if the owner says so, or the table shows it released. Record every takeover and release in the session log. If agents must work concurrently, each uses its own task branch cut from the current integration/phase branch head, and the owner merges.

**Safe write procedure.**
1. Read the branch HEAD SHA (`H0`) before starting.
2. Before changing an existing file, read its latest content and blob SHA; pass the blob SHA when the tool accepts one (this makes the write fail if the file changed).
3. Immediately before committing, re-read HEAD. If it is not `H0`, **stop**: another agent or the owner changed the branch. Reconcile; never overwrite blindly.
4. After committing, confirm the new commit's parent is `H0` and the files exist on GitHub. Multi-file push tools may have no head precondition, so this post-check is mandatory; if the parent is not `H0`, report it and do not force anything.

## 5. Escalate to the owner before deciding

Religious correctness; irreversible data loss; security-sensitive operations; repository permissions or branch protection; production publication; core product identity changes; merging.

Make other reasonable, reversible engineering decisions independently and document them (ADR or status file).

## 6. Tooling limits (re-verify every session; accounts can differ)

- Known from the Phase 0 audit on one account: file, branch, commit and pull-request tools exist, including PR check runs; **no tools to list workflow runs, read job logs or download artifacts**. Another account may have different tools; discover, do not assume.
- Binary files (for example `gradle-wrapper.jar`) must not be assumed uploadable through the connector; see the wrapper plan in [`docs/architecture/ci-build-strategy.md`](docs/architecture/ci-build-strategy.md).
- Do not recommend Claude Code, local Android Studio, or manual ZIP uploads. Workflow: Claude Chat -> GitHub connector -> GitHub Actions.

## 7. Engineering standards

Correctness, testability, security, accessibility, performance and data integrity all count. Domain logic is pure Kotlin with no Android dependencies. Every ledger and fiqh-rule behavior has tests ([`docs/testing/strategy.md`](docs/testing/strategy.md)). Do not weaken CI checks to turn a build green. Do not invent dependency versions; verify against authoritative sources. Risks: [`docs/architecture/risk-register.md`](docs/architecture/risk-register.md). Privacy: [`docs/security/privacy-model.md`](docs/security/privacy-model.md).

## 8. Session end protocol (handoff)

At the end of every meaningful session, in order:

1. Commit completed work; verify the commit exists on GitHub.
2. Record real verification evidence only.
3. Update `ENGINEERING_STATUS.md` (current verified state).
4. Update `AGENT_HANDOFF.md` (state, next action, blockers, branch ownership: release or hold).
5. Append one concise entry to `SESSION_LOG.md` (concrete work only; no chat transcripts, secrets or personal information).
6. Commit the documentation updates (`docs(handoff): ...`); verify the final commit.
7. Report the exact next task to the owner.

Handoff-only commits are expected to follow the handoff's "last verified commit"; a later agent confirms that by checking the changed files. Update documents with small precise edits; do not regenerate the roadmap or rewrite settled architecture.

## 9. Definition of done (any task)

Code or docs committed on a branch; result verified on GitHub; tests added or updated and actually run in CI (code phases); status, handoff and session log updated; open religious or security questions recorded.
