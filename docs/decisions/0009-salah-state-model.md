# ADR-0009: Salah state model with derived NEEDS_REVIEW

**Status:** Accepted

**Context.** An ignored notification or a lapsed window is not evidence the prayer was missed; auto-marking Missed would create false obligations.

**Decision.** Stored states: Pending, Completed (preferred / late-in-window), Missed, Completed-as-Qaza, Unknown, Excused. `NEEDS_REVIEW` is derived (Pending after window end) and never stored. Only explicit user action sets Missed or moves a prayer to Qaza. Every edit writes an immutable revision.

**Consequences.** The user reviews lapsed prayers consciously. The preferred/late split depends on rule FQ-006 and may initially be collapsed.
