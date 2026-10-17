# ADR-0011: GitHub Actions as the Android build environment

**Status:** Accepted (owner workflow)

**Context.** No local Android tooling is used. The only available agent tools are GitHub file, branch, commit and pull-request operations; Actions logs and artifacts are not directly readable.

**Decision.** CI on GitHub Actions builds, tests, lints and produces a debug APK artifact. Wrapper bootstrap is done by a one-time `workflow_dispatch` workflow to avoid binary upload. Failures are surfaced to the agent through check runs and a bot comment on the pull request. Checks are never weakened to pass. Details: `docs/architecture/ci-build-strategy.md`.

**Consequences.** Slower feedback than a local IDE; reproducible builds from clean runners. The agent cannot fetch artifacts and will state that.
