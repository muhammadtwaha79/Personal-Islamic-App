# ADR-0007: Fiqh profiles and versioned rule register

**Status:** Proposed (religious correctness: owner decision required)

**Context.** Rulings differ across schools and sometimes within a school. The app must not silently choose, and must not present unverified rules as fact.

**Decision.** Religious rules live as versioned data in `docs/fiqh/rule-register.md` and a derived machine-readable form added in Phase 3. The user explicitly confirms a profile (initial: Hanafi). Every rule has a verification status; unverified rules are configurable or inert. Records snapshot the register version used.

**Consequences.** Slower feature delivery for religious logic; far lower risk of presenting guesses as rulings. Requires the owner to nominate a qualified reviewer.
