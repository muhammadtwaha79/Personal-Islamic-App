# Engineering Status

*Read this first when resuming work. It records only what has actually been done and verified.*

## Current phase

**Phase 0: Product Architecture and Engineering Foundation. Documentation complete on branch `phase-0/foundation`, pending owner review and merge.**

## Last verified commit

- Documentation content verified on GitHub (each directory listing read back) at commit `717e7793b9f001672c40e7ba12bf6ed2ce595af2` on `phase-0/foundation`.
- The commit that adds this status file follows it; see the branch head for the exact SHA.
- `main` is still at `512e59d25e4f5ac4d749ab256228eac4596ccf77` (initial commit). Nothing from Phase 0 is on `main`.

## Last CI run

**None.** No workflow exists yet. No build, test or lint has ever run.

## Implemented features

**None.** There is no Android code, no database, no app, no APK.

Implemented in the repository (documents and config only): README, CLAUDE.md, project brief, requirements, domain models, Qaza ledger design, architecture overview, CI build strategy, test strategy, privacy model, fiqh rule register (all rules unverified), ADRs 0001-0013, roadmap, `.gitignore`, PR template.

## Verified so far

- GitHub connector authenticated as `muhammadtwaha79`; read access works.
- Write access works: branch, file commit and read-back succeeded on `test/claude-github-connection` (commit `52808d99f3131c0d03401e08b3c46210b3255439`).
- All Phase 0 files listed above exist on `phase-0/foundation` (directory listings read back from GitHub).

## Not verified

- Any claim about toolchain versions (deliberately none made).
- That `.gitignore` patterns behave as intended in practice.
- Whether the connector can upload binary files (assumed not).
- Anything religious: every register rule is `UNVERIFIED`.
- The CI design: it has never been executed.

## Pending features

Everything in Phases 1-9 of `docs/ROADMAP.md`.

## Known limitations

- The connector has no tools for workflow runs, job logs or artifacts. CI visibility is through pull-request check runs plus a planned bot failure comment.
- The agent cannot download or inspect the APK artifact; the owner downloads it from the Actions run page.
- Test branch `test/claude-github-connection` still exists (no branch-delete tool); do not merge it. Owner can delete it from the GitHub UI.

## Open questions for the owner

**Religious (need a qualified reviewer; see `docs/fiqh/rule-register.md`):** FQ-003 Hanafi Witr Qaza and treating the 365 Witr estimate as an obligation; FQ-004 order of Qaza; FQ-005 Asr and twilight conventions; FQ-006 preferred time; FQ-007 Tahajjud; FQ-008/009 Ishraq and Duha; FQ-010 Awwabeen; FQ-013 voluntary Qaza; FQ-014 how to estimate historical Qaza (the 365 figures are the owner's planning estimates, not verified records); FQ-015/016/018/019/020/022 fasting rules; FQ-021 Hijri method.

**Engineering / security (ADRs marked Proposed):** ADR-0007 fiqh profile mechanism; ADR-0012 no INTERNET permission and no location permission; ADR-0013 Hijri calendar approach. Confirm proposed minSdk 26.

## Next engineering task

After the owner merges Phase 0 (or authorizes continuing on the branch): **Phase 1 step 1** is to resolve the Gradle / Android Gradle Plugin / Kotlin / Compose / JDK / SDK compatibility matrix from official sources and record it, with retrieval date and links, in `docs/architecture/toolchain-matrix.md`; then add the wrapper bootstrap workflow.
