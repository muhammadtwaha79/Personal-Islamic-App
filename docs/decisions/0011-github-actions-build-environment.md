# ADR-0011: GitHub Actions as the Android build environment

**Status:** Accepted (owner workflow)

**Context.** No local Android tooling is used. The only available agent tools are GitHub file, branch, commit and pull-request operations; Actions logs and artifacts are not directly readable.

**Decision.** CI on GitHub Actions builds, tests, lints and produces a debug APK artifact. The Gradle wrapper is bootstrapped by a **push-triggered** workflow on a dedicated branch (not `workflow_dispatch`, which depends on default-branch placement) to avoid uploading binary files; see `docs/architecture/ci-build-strategy.md` section 3. Results are surfaced to the agent through check runs and a report comment on the pull request (success evidence: APK name, size, SHA-256; failure evidence: trimmed log lines). Checks are never weakened to pass.

**Consequences.** Slower feedback than a local IDE; reproducible builds from clean runners. The agent cannot fetch artifacts and will state that. Commits made by the workflow's `GITHUB_TOKEN` do not trigger further runs, so a follow-up commit through the connector is needed after bootstrap.
