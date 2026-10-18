# ADR-0016: AGP 9 built-in Kotlin and reproducible cloud builds

**Status:** Accepted for the Phase 1 technical foundation (2026-10-18).

## Context

The project is developed through GitHub connectors without a local Android SDK. Build verification must therefore be reproducible in GitHub Actions. The initial AGP 9.4 / Gradle 9.6 / JDK 17 pairing, Kotlin/Compose/KSP/Hilt versions and their compatibility were tested through a real cloud build.

## Decision

- Use Android Gradle Plugin 9.4.0 with built-in Kotlin, Gradle 9.6.0 and JDK 17.
- Do not apply `org.jetbrains.kotlin.android` or kapt. Use KSP with Room and Hilt.
- Commit and validate Gradle Wrapper files, including JAR checksum and distribution SHA-256.
- Run unit tests, Android lint and debug APK assembly through a read-only GitHub Actions job.
- Archive debug APK and test reports. Do not use an unverified local-build assertion as evidence.
- Never add Android INTERNET permission or analytics to solve build problems.
- Dependency versions and official reference links are recorded in [toolchain-matrix.md](../architecture/toolchain-matrix.md).

## Evidence

- Successful GitHub Actions workflow for implementation commit `00a9ed6ffb3b4d7ab0cb724f7b2e65a7c5cc8932`: https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39100930667
- Wrapper validation, compilation/test/lint/assembly, and APK artifact upload steps all concluded success.
- The APK is a debug build for personal evaluation, not a signed production release.
- Future dependency upgrades require fresh CI verification.
