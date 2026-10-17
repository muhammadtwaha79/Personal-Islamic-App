# ADR-0012: Privacy baseline: no network permission, no data in repo

**Status:** Accepted (owner-approved, 2026-10-17)

**Context.** Worship records are highly sensitive; the repository is public.

**Decision.** The app ships offline-first without the `INTERNET` permission, analytics, ads or crash reporting, and without unnecessary collection of personal data. No location permission; prayer-time location is manual or from a bundled list. Android auto-backup is disabled or restricted by data-extraction rules. No personal data, databases, exports or secrets are committed; `.gitignore` and a CI check enforce this. Adding any of these capabilities later requires a new ADR and owner approval.

**Consequences.** No remote crash diagnostics; users on a new device restore via manual encrypted backup (Phase 8). Because the debug APK is built in CI on a public repository, build artifacts must never contain personal data or secrets.
