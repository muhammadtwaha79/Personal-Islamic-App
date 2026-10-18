# Toolchain Compatibility Matrix (Phase 1, step 1)

*Retrieved: 2026-10-18. Status: **PARTIAL**. Only entries with an official source link are `VERIFIED`. Everything else is `UNKNOWN` and must not be guessed or filled from memory or third-party repositories. Rule source: [../phases/phase-1-plan.md](../phases/phase-1-plan.md), [ci-build-strategy.md](ci-build-strategy.md) section 6.*

## Verified

| Component | Version | Evidence | Source |
| --- | --- | --- | --- |
| Android Gradle Plugin (AGP) | **9.4.0** (stable, September 2026) | Release notes page (last updated 2026-10-16 UTC). AGP 9.5 exists only as a preview and is not used. | https://developer.android.com/build/releases/agp-9-4-0-release-notes |
| Gradle | **9.6.0** | AGP 9.4 lists Gradle minimum **9.6.0** and default **9.6.0**. Gradle 9.6.0 released 2026-06-18. | https://developer.android.com/build/releases/agp-9-4-0-release-notes ; https://github.com/gradle/gradle-distributions/releases/tag/v9.6.0 |
| Gradle 9.6.0 `bin` distribution SHA-256 | `bbaeb2fef8710818cf0e261201dab964c572f92b942812df0c3620d62a529a01` | Published checksum on the Gradle release page; independently matches the `distributionSha256Sum` in AOSP's `gradle-wrapper.properties` for `gradle-9.6.0-bin.zip`. | https://github.com/gradle/gradle-distributions/releases/tag/v9.6.0 ; https://android.googlesource.com/platform/frameworks/support/+/HEAD/gradle/wrapper/gradle-wrapper.properties |
| Gradle wrapper JAR SHA-256 (9.6.0) | `497c8c2a7e5031f6aa847f88104aa80a93532ec32ee17bdb8d1d2f67a194a9c7` | Published on the same release page. Use only to cross-check the bootstrapped jar. | https://github.com/gradle/gradle-distributions/releases/tag/v9.6.0 |
| JDK | **17** (AGP 9.4 minimum and default) | AGP 9.4 compatibility table. | https://developer.android.com/build/releases/agp-9-4-0-release-notes |
| SDK Build Tools | **36.0.0** (AGP 9.4 minimum and default) | AGP 9.4 compatibility table. | same |
| Maximum API level supported by AGP 9.4 | **37** | AGP 9.4 compatibility table. | same |

## Verified build-structure consequences of AGP 9

Source: https://developer.android.com/build/migrate-to-built-in-kotlin (last updated 2026-09-15 UTC).

- AGP 9.0+ enables **built-in Kotlin** by default. The `org.jetbrains.kotlin.android` plugin must **not** be applied (applying it fails the build).
- `kapt` is incompatible with built-in Kotlin. Use **KSP**. (Fallback `com.android.legacy-kapt` exists but is not wanted.)
- `android.kotlinOptions{}` is replaced by `kotlin.compilerOptions{}`; `jvmTarget` defaults to `android.compileOptions.targetCompatibility`.
- Kotlin source dirs: only `android.sourceSets{}` `kotlin` set is supported.
- Opting out (`android.builtInKotlin=false`, `android.newDsl=false`) is temporary and not possible in AGP 10. **Decision: do not opt out.**

## Kotlin (partially known)

- Kotlin **2.4.0** was released in June 2026 (JetBrains blog path `/2026/06/`; secondary sources disagree on the exact day, which does not affect the choice). Its release note states compatibility with Gradle 9.5.0. https://blog.jetbrains.com/kotlin/2026/06/kotlin-2-4-0-released/
- Kotlin **2.4.20** was released 2026-09-07. https://kotlinlang.org/docs/whatsnew2420.html
- **UNKNOWN:** which Kotlin version AGP 9.4 built-in Kotlin uses or requires by default; whether Kotlin 2.4.x declares support for Gradle 9.6.0; therefore the Kotlin version is **not yet chosen**.

## Unknown (no version chosen; each needs an official source before use)

| Component | What must be established |
| --- | --- |
| KSP | Version scheme and a release compatible with the chosen Kotlin and with AGP 9 built-in Kotlin. |
| Compose compiler plugin (`org.jetbrains.kotlin.plugin.compose`) | Expected to track the Kotlin version; confirm with Kotlin/Compose docs and AGP 9 interplay. |
| Compose BOM, Material 3, Activity Compose, Lifecycle | Current stable versions from the AndroidX release pages. |
| Navigation Compose | Current stable version and API (type-safe routes). |
| Room (runtime, ktx, compiler, testing) | Current stable line and KSP compatibility; schema export settings. |
| Hilt / Dagger, hilt-navigation-compose | Release that supports AGP 9 and built-in Kotlin (plugin application changed). |
| Coroutines, kotlinx-datetime (if used), JUnit, Turbine | Current stable versions. |
| compileSdk / targetSdk | AGP 9.4 supports up to 37; confirm whether API 37 is a stable platform and which packages the CI runner image provides. `minSdk = 26` is fixed (ADR-0014). |
| GitHub-hosted runner image | JDK and Android SDK packages preinstalled (platform, build-tools 36.0.0); whether `sdkmanager` installs are needed. |
| GitHub Actions (checkout, setup-java, setup-gradle, upload-artifact, wrapper validation) | Latest release tags and full commit SHAs resolved via the connector. |

## Method notes

- Search results from third-party repositories showed AGP 8.x-era version sets. They are stale and were discarded.
- No local build is possible from the agent sandbox: its network allow-list does not include Gradle or Google Maven hosts. All build verification must come from GitHub Actions check runs.

## Next action

Resolve every `UNKNOWN` row from official sources, then freeze the matrix and write `gradle/libs.versions.toml` from it. The Gradle wrapper bootstrap (step 3) needs the owner's Actions settings confirmation first (see [../phases/phase-1-plan.md](../phases/phase-1-plan.md)).
