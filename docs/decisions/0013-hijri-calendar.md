# ADR-0013: Hijri calendar: pluggable calculation with user offset

**Status:** Accepted (owner-approved as an architectural approach, 2026-10-17). **No calculation algorithm or library has been selected.**

**Context.** Calculated Hijri dates may differ from local moon sighting by a day.

**Decision.** Define a `HijriCalendar` abstraction with a configurable method, a global local-date adjustment (day offset), and **monthly overrides** entered by the user. The specific calculation algorithm or library is **not selected here**; it is chosen in Phase 5 only after a documented evaluation against official sources. Stored records snapshot the Hijri date and calendar configuration at creation time. The UI never presents a calculated date as the sighting result.

**Consequences.** The user may need to adjust dates around Ramadan and Eid. Historical records remain stable even if configuration changes later.
