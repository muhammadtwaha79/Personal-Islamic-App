# ADR-0002: Native Android with Kotlin, Jetpack Compose, Material 3

**Status:** Accepted (follows owner's specified stack)

**Context.** Single-platform personal app needing reliable local storage, notifications, and a polished UI.

**Decision.** Kotlin, Jetpack Compose, Material 3, Navigation Compose, Hilt, Coroutines/Flow, DataStore, WorkManager. Versions are chosen in Phase 1 from official compatibility sources, never guessed.

**Consequences.** Android-only. Modern, well-supported tooling; Compose UI tests require an emulator job in CI.
