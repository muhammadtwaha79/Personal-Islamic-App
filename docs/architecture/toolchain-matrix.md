# Toolchain Compatibility Matrix (Phase 1, step 1)

*Retrieved: 2026-10-18. Status: **FROZEN for the first Phase 1 build; compatibility is only proven when CI passes.** `VERIFIED` = read from an official source cited here. `UNVERIFIED` = chosen with a stated reason; CI is the test. No version below came from memory or from third-party sample repositories.*

## Pinned versions

| Component | Version | Status | Source / reason |
| --- | --- | --- | --- |
| Android Gradle Plugin | **9.4.0** | VERIFIED | https://developer.android.com/build/releases/agp-9-4-0-release-notes (stable, Sept 2026; max API 37; requires Gradle >= 9.6.0, JDK 17, Build Tools 36.0.0) |
| Gradle | **9.6.0** | VERIFIED | Same AGP page (min = default = 9.6.0). `bin` SHA-256 `bbaeb2fef8710818cf0e261201dab964c572f92b942812df0c3620d62a529a01`; wrapper jar SHA-256 `497c8c2a7e5031f6aa847f88104aa80a93532ec32ee17bdb8d1d2f67a194a9c7`: https://github.com/gradle/gradle-distributions/releases/tag/v9.6.0 (bin checksum cross-checked against AOSP's `gradle-wrapper.properties`). Gradle 9.7.x exists but is not needed. |
| JDK | **17** (Temurin via setup-java) | VERIFIED | AGP 9.4 minimum and default |
| Kotlin | **2.4.0** | UNVERIFIED (compat with Gradle 9.6.0) | Released June 2026: https://blog.jetbrains.com/kotlin/2026/06/kotlin-2-4-0-released/ (states Gradle 9.5.0 compatibility). Chosen over 2.4.20 (2026-09-07) because KSP release notes explicitly mention Kotlin 2.4.0 support and nothing verifies 2.4.20 with KSP/Hilt. AGP 9 built-in Kotlin is used, no `kotlin-android` plugin. |
| Kotlin Compose compiler plugin | **2.4.0** (same as Kotlin) | VERIFIED rule, UNVERIFIED instance | `org.jetbrains.kotlin.plugin.compose` tracks the Kotlin version. Also pulls KGP 2.4.0 above AGP 9's bundled KGP (AGP 9.0 notes: https://developer.android.com/build/releases/agp-9-0-0-release-notes). |
| KSP | **2.3.11** (2026-08-03) | VERIFIED existence and fixes | https://github.com/google/ksp/releases . KSP 2.3.x versions independently of Kotlin; 2.3.10 notes: works with Kotlin 2.4.0 module names and AGP 9 built-in Kotlin R-class fix. Known open hazard google/ksp#3053 (Room + `@Parcelize` under built-in Kotlin); Parcelize is not used. |
| Hilt / Dagger | **2.60.1** | VERIFIED as documented current | https://dagger.dev/hilt/gradle-setup.html (uses `ksp("com.google.dagger:hilt-compiler:2.60.1")`). Hilt plugin 2.59+ is the first line supporting AGP 9 (release notes of dagger-2.59; early 2.59 had a runtime-artifact bug, issue #5099, so a later patch line is used). |
| Compose BOM | **2026.09.00** | UNVERIFIED mapping | https://developer.android.com/develop/ui/compose/bom (newest cached copy shows 2026.09.00; others 2026.08.00). Compose libraries stable 1.12.1, Material 3 stable 1.4.0 as of 2026-10-07: https://developer.android.com/jetpack/androidx/releases/compose . Exact BOM mapping not read. |
| Activity Compose | **1.13.0** | VERIFIED | https://developer.android.com/jetpack/androidx/releases/activity (stable, 2026-09-23) |
| Navigation Compose | **2.10.2** | VERIFIED | https://developer.android.com/latest-updates (stable, 2026-09-23). Navigation 3 (1.2.0 stable) exists; owner brief says Navigation Compose, so Navigation 2 is used. |
| Room (runtime, ktx, compiler) | **2.8.5** | VERIFIED | https://developer.android.com/jetpack/androidx/versions/stable-channel (2026-09-09). A Room 3 line was not investigated. |
| JUnit | **4.13.2** | UNVERIFIED | Last JUnit 4 line; CI dependency resolution is the check. |
| compileSdk / targetSdk / minSdk | **37 / 36 / 26** | UNVERIFIED (37) | AGP 9.4 supports up to API 37 (VERIFIED). compileSdk 37 is chosen so any AndroidX AAR requiring it passes metadata checks; whether the runner or AGP can provide platform 37 is tested by CI. targetSdk 36 is conservative. minSdk 26 per ADR-0014. |

## GitHub Actions (pinned by full commit SHA, resolved through the GitHub connector on 2026-10-18)

| Action | Tag | Commit SHA | Note |
| --- | --- | --- | --- |
| actions/checkout | v7.0.1 | `3d3c42e5aac5ba805825da76410c181273ba90b1` | latest release (2026-07-20) |
| actions/setup-java | v6.0.1 | `de7274f081f381c8f8158605e0321c36c376e2e6` | v6.1.0 (2026-10-14) skipped: less than two weeks old |
| gradle/actions (setup-gradle, wrapper-validation) | v6.4.0 | `3f5f9adaf7d9fecd50b5935e54106014257a94e6` | latest release (2026-09-28) |
| actions/upload-artifact | v7.0.1 | `043fb46d1a93c77aae656e7c1c64a875d1fc6a0a` | v7.0.2 (2026-10-07) skipped: less than two weeks old |

Runner: `ubuntu-24.04`. Its preinstalled Android SDK packages are **not verified**; the workflow accepts licenses and lets AGP/sdkmanager provide missing packages.

## Build-structure rules from AGP 9 (VERIFIED: https://developer.android.com/build/migrate-to-built-in-kotlin)

- Built-in Kotlin is on by default. Do **not** apply `org.jetbrains.kotlin.android`. Do not opt out (not possible in AGP 10).
- kapt is incompatible; use KSP.
- `kotlinOptions{}` is replaced by `kotlin.compilerOptions{}`; `jvmTarget` follows `compileOptions.targetCompatibility`.

## Deferred (not needed in Phase 1; verify from official sources before adding)

Lifecycle (explicit), hilt-navigation-compose, kotlinx-coroutines (explicit; coroutines arrive transitively via Room KTX), DataStore, WorkManager, Roborazzi/Robolectric, instrumented-test libraries, Room Gradle plugin.

## Decisions recorded here pending ADR formalization

1. **AGP 9 with built-in Kotlin, no opt-out; KSP only.**
2. **GitHub Actions permissions (owner decision 2026-10-18):** repository default workflow permissions stay **restricted**. Every workflow declares top-level `permissions: contents: read` and elevates per job only. Normal CI jobs: `contents: read`. The trusted, push-triggered wrapper bootstrap job alone has `contents: write`. The PR report job alone has `pull-requests: write` and `actions: read`, runs no repository code, and is restricted to same-repository PRs. No write token is ever given to jobs that run untrusted pull-request code.
3. **Wrapper bootstrap trigger:** push to `phase-1/foundation` limited by path filters (the wrapper properties file and the bootstrap workflow). This replaces the separate `phase-1/wrapper-bootstrap` branch from the earlier design because the connector has no branch-merge tool. It stays trusted: only users with write access can push to that branch.

## Method notes

- Third-party sample repositories showed stale AGP 8-era versions and were discarded as evidence.
- The agent sandbox cannot reach Gradle or Google Maven; no local build is possible. The first CI run is the first real compatibility test; any mismatch above will be corrected here with the CI evidence.
