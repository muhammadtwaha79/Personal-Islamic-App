# ADR-0012: Privacy baseline: no network permission, no data in repo

**Status:** Proposed (security-sensitive: owner approval required)

**Context.** Worship records are highly sensitive; the repository is public.

**Decision.** The app ships without the `INTERNET` permission, analytics, ads or crash reporting. No location permission; prayer-time location is manual or from a bundled list. Android auto-backup is disabled or restricted by data-extraction rules. No personal data, databases, exports or secrets are committed; `.gitignore` and a CI check enforce this. Adding any of these capabilities later requires a new ADR and owner approval.

**Consequences.** No remote crash diagnostics; users on a new device restore via manual encrypted backup (Phase 8).
