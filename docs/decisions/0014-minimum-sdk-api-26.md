# ADR-0014: Minimum Android SDK is API 26

**Status:** Accepted (owner-approved, 2026-10-17)

**Context.** `java.time` is available natively from API 26, avoiding core-library desugaring for the time model (ADR-0008). API 26 also gives modern notification channels and background execution rules.

**Decision.** `minSdk = 26` (Android 8.0). compileSdk and targetSdk are set in Phase 1 from official sources and recorded in the toolchain matrix; they are not fixed here.

**Consequences.** Devices below Android 8.0 are unsupported. Lowering minSdk later would require adding desugaring and re-testing time logic.
