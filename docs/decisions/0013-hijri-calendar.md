# ADR-0013: Hijri calendar: pluggable calculation with user offset

**Status:** Proposed (religious dependency: FQ-021)

**Context.** Calculated Hijri dates may differ from local moon sighting by a day.

**Decision.** Define a `HijriCalendar` abstraction. Provide a calculation-based implementation (the specific algorithm or library is chosen in Phase 5 after evaluation) plus a global day-offset and per-month overrides entered by the user. Stored records snapshot the Hijri date and the calendar configuration at creation time. The UI never presents a calculated date as the sighting result.

**Consequences.** The user may need to adjust dates around Ramadan and Eid. Historical records remain stable even if configuration changes later.
