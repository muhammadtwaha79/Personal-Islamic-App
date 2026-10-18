# Architecture Decision Records

Format: Context, Decision, Consequences. Status values: `Proposed` (needs owner approval), `Accepted`, `Superseded by ADR-xxxx`. The owner reviewed Phase 0 on 2026-10-17 and approved the decisions marked below.

| ADR | Title | Status |
| --- | --- | --- |
| 0001 | Record architecture decisions | Accepted |
| 0002 | Native Android with Kotlin, Compose, Material 3 | Accepted |
| 0003 | Single Gradle module with enforced package boundaries | Accepted |
| 0004 | Room/SQLite, offline-only persistence | Accepted |
| 0005 | Append-only Qaza ledger | Accepted |
| 0006 | Shared obligation ledger for Salah and fasting | Accepted |
| 0007 | Fiqh profiles and versioned rule register | Accepted (owner-approved approach, 2026-10-17) |
| 0008 | Time model | Accepted |
| 0009 | Salah state model with derived NEEDS_REVIEW | Accepted |
| 0010 | Identifiers: UUID plus ledger sequence | Accepted |
| 0011 | GitHub Actions as the Android build environment | Accepted |
| 0012 | Privacy baseline: no network permission, no data in repo | Accepted (owner-approved, 2026-10-17) |
| 0013 | Hijri calendar: pluggable calculation with user offset | Accepted (owner-approved approach; algorithm NOT selected) |
| 0014 | Minimum Android SDK is API 26 | Accepted (owner-approved, 2026-10-17) |
| 0015 | Disputed conventions are configurable; no silent defaults | Accepted (owner decisions, 2026-10-17) |

Approval of an ADR approves the *architectural approach*. It does not verify any religious rule; rules stay `UNVERIFIED` in `docs/fiqh/rule-register.md` until reviewed.
