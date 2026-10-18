# CI Build Strategy (Phase 1 design)

Status: design only, revised after Phase 0 review. **No workflow exists yet.** Nothing below has been run.

## 1. Goal

GitHub Actions is the Android build environment: resolve dependencies, compile Kotlin, run unit tests, run Android lint, build a debug APK, upload artifacts, and surface results in a form this workflow's AI agent can actually read.

## 2. Tooling constraints discovered in the Phase 0 audit

- The GitHub connector has file, branch, commit and pull-request tools, including pull-request **check runs**.
- It has **no** tool to list workflow runs, read job logs, or download artifacts.
- Binary upload through the connector is unverified; do not rely on it.

## 3. Gradle wrapper bootstrap (binary-safe, no button needed)

`gradle/wrapper/gradle-wrapper.jar` is binary. Plan:

1. Commit text files only: `settings.gradle.kts`, root and app `build.gradle.kts`, `gradle/libs.versions.toml`, `gradle/wrapper/gradle-wrapper.properties`.
2. Add `bootstrap-wrapper.yml`, triggered by **`push` to the dedicated branch `phase-1/wrapper-bootstrap`** (not `workflow_dispatch`). Reason: a `workflow_dispatch` workflow generally appears in the Actions UI only once it exists on the default branch, which would force merging to `main` first. A push-triggered workflow runs from the branch it is pushed to.
3. The job installs the pinned Gradle version with the official setup action, runs `gradle wrapper --gradle-version <pinned>`, and commits `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar` back to that branch with `contents: write` limited to this workflow. It does nothing if the wrapper already exists (prevents loops).
4. **Known GitHub behavior:** commits pushed with the default `GITHUB_TOKEN` do not trigger new workflow runs. Therefore after the bootstrap commit, the agent makes a small follow-up commit through the connector so the normal CI runs on the branch.
5. CI includes the official wrapper validation step so a tampered jar fails the build. The distribution checksum is set from Gradle's official published values, verified at that time.
6. Whether the repository's Actions settings (workflow permissions) allow the write is **unverified**; if the bootstrap fails on permissions the owner changes one setting (see `docs/phases/phase-1-plan.md`).

## 4. Main workflow `android-ci.yml` (pull requests and pushes to development branches)

Separate jobs for clear failure attribution:

1. `validate`: wrapper validation; forbidden-file check (blocks `*.db`, `*.sqlite*`, `*.jks`, `*.keystore`, export/backup patterns).
2. `build-test-lint`: JDK setup, Gradle setup with caching, `./gradlew testDebugUnitTest lintDebug assembleDebug --stacktrace`.
3. `artifacts`: upload debug APK, unit-test reports, lint reports with deliberate retention. For a public repository, assume anyone who can view the repo may be able to download artifacts; builds must therefore never embed personal data or secrets (ADR-0012).
4. `report` (runs on success **and** failure): writes a job summary and one updating pull-request comment via a narrowly scoped `pull-requests: write` token.
   - On success: APK file name, size and SHA-256, number of tests run, lint result. This is the evidence that an APK was actually built.
   - On failure: failing Gradle task and trimmed relevant log lines.
5. Later (Phase 2+): `instrumented-tests` on an emulator; Compose UI tests; migration tests.

If the workflow file itself is invalid, no run (and no comment) exists. The agent treats "no check run appeared" as a failure to investigate, and the owner can paste the Actions error from the UI.

## 5. Permissions and security

- Workflow-level `permissions: contents: read`; elevate per job only where required.
- Pin third-party actions to full commit SHAs, resolved from official release tags via the connector at implementation time.
- No secrets are required for debug builds. Release signing keys (Phase 9) live only in Actions secrets, never in the repo.
- Debug APK uses the standard debug signing; personal testing only.
- Logs on a public repository are public: CI must never print data from the app.

## 6. Toolchain compatibility (to be resolved in Phase 1, not guessed now)

Cross-check from official sources (Android Gradle Plugin release notes, Gradle compatibility matrix, Kotlin and Compose documentation, Hilt/KSP release notes, runner image documentation):

- Gradle <-> Android Gradle Plugin <-> JDK
- Kotlin <-> KSP <-> Compose compiler plugin
- compileSdk/targetSdk and SDK packages present on the GitHub-hosted runner (install via `sdkmanager` with license acceptance only if absent); `minSdk = 26`.

The resolved matrix and its sources go in `docs/architecture/toolchain-matrix.md` with a retrieval date.

## 7. Rules

- Never weaken checks to get green: no `continue-on-error` on tests or lint, no lint baseline to hide new issues without an ADR, no deleting failing tests.
- Every failure is diagnosed from evidence (check-run output, the report comment, or logs the owner pastes).
- A build is only reported successful if a check run says so; an APK is only reported as existing if the report comment shows its size and SHA-256 and the artifact step succeeded.

## 8. Retrieving the APK

The owner downloads the artifact from the workflow run page on GitHub (login required). The agent cannot download artifacts with current tools and will say so rather than imply otherwise.
