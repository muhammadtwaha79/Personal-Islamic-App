# CI Build Strategy (Phase 1 design)

Status: design only. **No workflow exists yet.** Nothing below has been run.

## 1. Goal

GitHub Actions is the Android build environment: resolve dependencies, compile Kotlin, run unit tests, run Android lint, build a debug APK, upload artifacts, and surface failures in a form this workflow's AI agent can actually read.

## 2. Tooling constraints discovered in the Phase 0 audit

- The GitHub connector has file, branch, commit and pull-request tools, including pull-request **check runs**.
- It has **no** tool to list workflow runs, read job logs, or download artifacts.
- Binary upload through the connector is unverified; do not rely on it.

## 3. Gradle wrapper bootstrap (binary-safe)

`gradle/wrapper/gradle-wrapper.jar` is binary. Plan:

1. Commit text files only: `settings.gradle.kts`, root and app `build.gradle.kts`, `gradle/libs.versions.toml`, `gradle/wrapper/gradle-wrapper.properties`.
2. Add a manually triggered workflow `bootstrap-wrapper.yml` (`workflow_dispatch`) that installs the chosen Gradle version using the official Gradle setup action, runs `gradle wrapper --gradle-version <pinned>`, and commits `gradlew`, `gradlew.bat`, and the jar to the working branch using the job's `GITHUB_TOKEN` with `contents: write`, scoped to that workflow only.
3. Normal CI includes the official Gradle wrapper validation step so a tampered jar fails the build.
4. Distribution URL and checksum (`distributionSha256Sum`) are set from Gradle's official published values, verified at that time.

The owner triggers the one-time bootstrap from the Actions tab (a single button, no local tools).

## 4. Main workflow `android-ci.yml` (on pull requests and pushes to development branches)

Jobs (separate for clear failure attribution):

1. `validate`: wrapper validation, secret scan hygiene, forbidden-file check (blocks `*.db`, `*.jks`, exports).
2. `build-test-lint`: JDK setup, Gradle setup with caching, `./gradlew testDebugUnitTest lintDebug assembleDebug --stacktrace`.
3. `artifacts`: upload debug APK, unit-test XML/HTML reports, lint reports (retention set deliberately).
4. `report-failure` (runs only on failure): extract the failing task name and trimmed relevant log lines into the job summary and, via a narrowly scoped `pull-requests: write` token, post one updating comment on the PR. This is how the agent reads CI failures despite lacking log tools.
5. Later (Phase 2+): `instrumented-tests` on an Android emulator job; Compose UI tests; migration tests.

## 5. Permissions and security

- Workflow-level `permissions: contents: read`; elevate per job only where required.
- Pin third-party actions to full commit SHAs, resolved from official release tags via the connector (`get_latest_release` / `get_tag`) at implementation time.
- No secrets are required for debug builds. Release signing keys (Phase 9) live only in Actions secrets, never in the repo.
- Debug APK uses the standard debug signing; it is for personal testing only.

## 6. Toolchain compatibility (to be resolved in Phase 1, not guessed now)

Must be cross-checked from official sources (Android Gradle Plugin release notes, Gradle compatibility matrix, Kotlin and Compose documentation, Hilt/KSP release notes, runner image documentation):

- Gradle <-> Android Gradle Plugin <-> JDK
- Kotlin <-> KSP <-> Compose compiler plugin
- compileSdk/targetSdk and the SDK packages present on the GitHub-hosted runner (install via `sdkmanager` with license acceptance only if absent)

The resolved matrix and its sources are written to `docs/architecture/toolchain-matrix.md` with a retrieval date.

## 7. Rules

- Never weaken checks to get green: no `continue-on-error` on tests or lint, no lint baseline to hide new issues without an ADR, no deleting failing tests.
- Every failure is diagnosed from evidence (check-run output, the failure comment, or logs the owner pastes).
- A build is only reported as successful if a check run says so; an APK is only reported as existing if the artifact upload step succeeded.

## 8. Retrieving the APK

The owner downloads the artifact from the workflow run page on GitHub (login required). The agent cannot download artifacts with current tools and will say so rather than imply otherwise.
