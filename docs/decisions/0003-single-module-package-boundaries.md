# ADR-0003: Single Gradle module with enforced package boundaries

**Status:** Accepted

**Context.** One developer, one app. Multi-module adds build and configuration cost that cloud CI makes slower.

**Decision.** One `app` module. Packages `core`, `domain`, `data`, `ui`, `di` with the rule `ui -> domain <- data`; `domain` is pure Kotlin. An architecture test enforces the rule from Phase 1.

**Consequences.** Faster builds and simpler setup. If domain logic needs stricter isolation later, extract a pure-Kotlin `domain` module (a mechanical move because boundaries are already enforced).
