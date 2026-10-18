# Phase 1 Plan: Native Android Foundation + GitHub Actions + First Compiled APK

Status: **plan only. Phase 1 has not started.** It begins only after the owner authorizes it (merge of PR #1, or instruction to continue on a branch).

## Objective

A minimal Android application that compiles in GitHub Actions, passes unit tests and lint, and produces a debug APK whose existence is evidenced by the CI report (file name, size, SHA-256). **No worship features** are built in Phase 1.

## Scope guardrails

- The app is a technical scaffold: one launcher activity showing the app name, clearly not a feature. No fake screens, no simulated persistence.
- `minSdk = 26`. No INTERNET permission. Backup restrictions in the manifest.
- Room is added with an empty-but-real database and schema export so the migration test harness can exist; no ledger tables yet.

## Owner pre-flight checklist (one-time, in the GitHub web UI)

1. Repository **Settings -> Actions -> General**: confirm Actions are enabled (allow actions).
2. Same page, **Workflow permissions**: choose **Read and write permissions** (needed for the wrapper bootstrap commit and PR comments). This is unverified as strictly necessary; if explicit job permissions suffice, it can be tightened afterwards.
3. Tell the agent when done. The agent cannot inspect these settings.

## Steps (small, reviewable commits on branch `phase-1/foundation`)

1. **Toolchain matrix.** Research official sources (AGP, Gradle, Kotlin, KSP, Compose, Hilt, JDK, runner image, SDK levels). Write `docs/architecture/toolchain-matrix.md` with links and retrieval date. No versions are guessed.
2. **Project skeleton (text only).** `settings.gradle.kts`, root and `app` `build.gradle.kts`, `gradle/libs.versions.toml`, wrapper properties, manifest, one activity, theme stub (emerald/ivory/gold tokens), string resources (English; Roman Urdu locale experiment).
3. **Wrapper bootstrap.** Branch `phase-1/wrapper-bootstrap` with the push-triggered workflow; verify the jar and scripts appear; merge into the working branch.
4. **Main CI.** `android-ci.yml` with jobs `validate`, `build-test-lint`, `artifacts`, `report`; action versions pinned by SHA resolved via the connector.
5. **Iterate to green.** Read check runs and report comments, fix root causes, never weaken checks.
6. **Quality gates.** Architecture test (domain purity), forbidden-file check, a placeholder-free unit test that exercises real code (for example `Clock`/`IdGenerator` abstractions).
7. **Close out.** Update `ENGINEERING_STATUS.md` with the real run reference, APK size and SHA-256 from the report, and open the Phase 1 PR.

## Acceptance criteria

- PR check run for the head commit concludes success for build, unit tests and lint.
- The report comment lists an APK with size and SHA-256 and the artifact upload step succeeded.
- Wrapper validation passes; no secrets or data files in the repository.
- Manifest contains no INTERNET permission (checked in CI).
- Status file states exactly what was verified.

## What the owner will need to do

The pre-flight settings above, and later download the APK artifact from the Actions run page and install it on a device (the agent cannot download or run it). The owner should report any install or launch problem.

## Out of scope

Salah, Qaza, fasting features; prayer-time computation; notifications; backup. These start in Phase 2 or later.

## Known risks

See `docs/architecture/risk-register.md` (R5-R10, R15, R17 are most relevant).
