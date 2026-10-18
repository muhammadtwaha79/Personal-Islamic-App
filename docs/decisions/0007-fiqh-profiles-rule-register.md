# ADR-0007: Fiqh profiles and versioned rule register

**Status:** Accepted (owner-approved as an architectural approach, 2026-10-17)

**Context.** Rulings differ across schools and sometimes within a school. The app must not silently choose, and must not present unverified rules as fact.

**Decision.** Religious rules live as versioned data in `docs/fiqh/rule-register.md` and a derived machine-readable form added in Phase 3. **Hanafi is the initial supported profile.** The user must explicitly confirm the profile before any profile-driven content (such as the Sunnah table or the Witr category) is shown or enabled. Every rule has a verification status; **unverified rules remain `UNVERIFIED`** and stay configurable or inert. Records snapshot the register version used.

**Consequences.** Slower feature delivery for religious logic; far lower risk of presenting guesses as rulings. Approval of this ADR does not verify any rule. Open religious questions do not block the Android engineering foundation.
