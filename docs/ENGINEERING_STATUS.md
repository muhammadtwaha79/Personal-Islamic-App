# Engineering Status

*Read this first when resuming work. It records only what has actually been done and verified.*

## Current phase

**Phase 0: Product Architecture and Engineering Foundation. Documentation complete on branch `phase-0/foundation`; pull request #1 is open and awaiting the owner's merge.** Owner review of 2026-10-17 has been incorporated.

## Pull request

- PR #1: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/1 (open, not draft, not merged; `mergeable_state` was `clean` when read).
- `main` is still at `512e59d25e4f5ac4d749ab256228eac4596ccf77`. Nothing is merged. The agent must not merge without explicit owner authorization.

## Last verified commit

- Branch `phase-0/foundation` head is the commit that adds this file; read the branch head for the exact SHA.
- Earlier Phase 0 commits verified by directory listing: `717e7793b9f001672c40e7ba12bf6ed2ce595af2` (roadmap), `4ff7f082243c1ab0a53e7d1272fc56959091fc6d` (first status), `83fe87947bb741f1eadcb4965e9eb3fc0e16045f` (owner decisions and ledger hardening).

## Last CI run

**None.** No workflow exists. No build, test or lint has ever run.

## Implemented features

**None.** There is no Android code, no database, no app, no APK. Phase 0 documentation does not constitute a built or verified Android application.

## Owner decisions incorporated (2026-10-17)

- `minSdk = 26` (ADR-0014).
- ADR-0007 accepted: Hanafi is the initial profile; the user must explicitly confirm it; unverified rules remain `UNVERIFIED`.
- ADR-0012 accepted: offline-first, no INTERNET permission initially, no analytics or ads, no unnecessary personal data collection.
- ADR-0013 accepted as an approach: configurable Hijri calendar with local adjustment and monthly overrides; **no algorithm selected**.
- ADR-0015: Asr convention configurable and never silently selected; Qaza ordering not enforced; Witr is a separate configurable category never added without user confirmation; 365 per Fard category supported as an editable suggestion; manual estimate corrections supported.

## Verified so far

- GitHub connector authenticated as `muhammadtwaha79`; read and write access work.
- All Phase 0 files exist on `phase-0/foundation` (directory listings read back from GitHub).
- PR #1 exists (read back through the connector).

## Not verified

- Everything about building: toolchain versions (deliberately none chosen), Actions settings, whether the CI design works.
- That `.gitignore` patterns behave as intended.
- Connector binary upload (assumed unavailable).
- Roman Urdu locale handling in Android.
- Anything religious: every register rule is `UNVERIFIED`.

## Pending features

Everything in Phases 1-9 of `docs/ROADMAP.md`. Phase 1 detail: `docs/phases/phase-1-plan.md`.

## Known limitations

- The connector has no tools for workflow runs, job logs or artifacts. CI visibility is through check runs plus a planned report comment.
- The agent cannot download or inspect the APK; the owner downloads it from the Actions run page.
- Test branch `test/claude-github-connection` still exists (no branch-delete tool); do not merge it. The owner can delete it in the GitHub UI.

## Open questions

**Religious (need a qualified reviewer, all still open):** FQ-003 whether Witr Qaza is obligatory for this user; FQ-004 Qaza order; FQ-005 which Asr/twilight convention applies; FQ-006 preferred time; FQ-007 Tahajjud; FQ-008/009 Ishraq and Duha; FQ-010 Awwabeen; FQ-013 voluntary Qaza; FQ-014 historical Qaza estimation; FQ-015/016/018/019/020/022 fasting rules; FQ-021 Hijri method. These do not block Phase 1.

**Design:** O1 in `qaza-accounting.md` (lowering an estimate below completed Qaza), needed before Phase 4.

**Architecture risks:** see `docs/architecture/risk-register.md`.

## Next engineering task

On owner authorization: complete the owner pre-flight checklist in `docs/phases/phase-1-plan.md`, then Phase 1 step 1 (toolchain compatibility matrix from official sources).
