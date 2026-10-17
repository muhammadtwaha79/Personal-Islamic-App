# ADR-0008: Time model

**Status:** Accepted

**Context.** Prayer days cross midnight; users travel and change time zones; Hijri dates depend on local settings. Re-deriving a local date from an instant later gives wrong history.

**Decision.** Store event times as UTC instants plus the IANA zone id where local meaning matters. Store the *occurrence date* of a prayer or fast as an explicit local civil date, never recomputed. Use `java.time` (minSdk proposal makes it native). Inject `Clock` everywhere.

**Consequences.** Slightly more columns. Correct history across travel and DST. Tests control time deterministically.
