# Engineering Status

*Current verified project state only. Next-agent instructions are in [AGENT_HANDOFF.md](AGENT_HANDOFF.md); history is in [SESSION_LOG.md](SESSION_LOG.md); rules are in [../CLAUDE.md](../CLAUDE.md). Statements are labelled `PLANNED`, `IMPLEMENTED`, `VERIFIED` or `BLOCKED`.*

## Current phase

**Phase 0: Product Architecture and Engineering Foundation.** Documentation is complete on `phase-0/foundation`; PR #1 is open and awaiting the owner's merge decision. Phase 1 has **not** started and must not start before owner authorization. Phase details: [ROADMAP.md](ROADMAP.md).

## Repository state (as last read from GitHub)

- `main`: `512e59d25e4f5ac4d749ab256228eac4596ccf77` (initial commit), unprotected. Nothing from Phase 0 is merged. `VERIFIED`
- `phase-0/foundation`: documentation branch. Exact head: read it from GitHub; the handoff records the last verified commit. `VERIFIED`
- PR #1: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/1 (open, not draft). `VERIFIED`
- `test/claude-github-connection`: connectivity test branch; do not merge. `VERIFIED`

## CI

No workflow exists. No build, test or lint has ever run. Last CI run: **none**.

## What exists

- `IMPLEMENTED` and `VERIFIED` by read-back: documentation set (README, CLAUDE.md, brief, requirements, domain models, Qaza ledger design, architecture overview, CI strategy, risk register, test strategy, privacy model, fiqh rule register, ADR-0001 to ADR-0015, roadmap, Phase 1 plan, status, handoff, session log), `.gitignore`, PR template.
- **No Android code, database, app or APK.** Phase 0 documentation does not constitute a built or verified Android application.
- Phases 1-9: `PLANNED`.

## Decisions

All accepted architecture decisions are in [decisions/README.md](decisions/README.md) (ADR-0001 to ADR-0015, including owner approvals of 2026-10-17). Not repeated here.

## Not verified

Toolchain versions (none chosen), repository Actions settings, whether the CI design works, `.gitignore` behavior, connector binary upload, Roman Urdu locale handling, and every religious rule (all `UNVERIFIED` in [fiqh/rule-register.md](fiqh/rule-register.md)).

## Known limitations

- On the account that did Phase 0 the connector has no Actions run, log or artifact tools; see tooling rules in [../CLAUDE.md](../CLAUDE.md). Other accounts may differ.
- The agent cannot download or run the APK; the owner does.
- No branch protection on `main` (owner action recommended; not changed by agents).

## Open questions

- Religious (need a qualified reviewer, all open, none block Phase 1): see "Questions that remain open" in [fiqh/rule-register.md](fiqh/rule-register.md).
- Design: open item O1 in [domain/qaza-accounting.md](domain/qaza-accounting.md), needed before Phase 4.
- Architecture risks: [architecture/risk-register.md](architecture/risk-register.md).

## Next task

See "Next exact action" in [AGENT_HANDOFF.md](AGENT_HANDOFF.md).
