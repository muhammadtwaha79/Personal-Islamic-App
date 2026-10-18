# Ibadah Companion

A private, offline-first Android application for tracking, managing and reflecting on personal Islamic worship: obligatory Salah, Sunnah and Nafl prayers, Qaza recovery, and fasting.

- Application ID (working): `com.muhammadtwaha.ibadahcompanion`
- Platform: native Android (Kotlin, Jetpack Compose, Material 3, Room)
- Initial user: one individual. No account, no cloud sync, no ads, no analytics.

## Current status

**Phase 0 (product architecture and engineering foundation) - documentation only.**

There is **no Android code, no build, and no APK yet.** Nothing in this repository should be read as an implemented feature unless `docs/ENGINEERING_STATUS.md` lists it as implemented and verified. Phase 0 documentation does not constitute a built or verified Android application.

## Product principles

1. Worship is not ordinary habit tracking. Points, streaks and percentages never represent spiritual rank or acceptance by Allah.
2. Every obligatory worship record is accounted for accurately; historical obligations never disappear because of bugs.
3. Religious accuracy takes priority over feature count. This app is a tracking tool, not a source of fatwas. All religious rules are `UNVERIFIED` until reviewed.
4. Privacy, reliability and maintainability are non-negotiable.

## Documentation map

| Document | Purpose |
| --- | --- |
| `CLAUDE.md` | Permanent engineering instructions for AI-assisted sessions |
| `docs/PROJECT_BRIEF.md` | Vision, scope, non-goals |
| `docs/ROADMAP.md` | Phases 0-9 |
| `docs/ENGINEERING_STATUS.md` | Current state; read this first when resuming work |
| `docs/phases/phase-1-plan.md` | Detailed plan for Phase 1 (not started) |
| `docs/architecture/overview.md` | System architecture |
| `docs/architecture/ci-build-strategy.md` | GitHub Actions Android build plan (Phase 1) |
| `docs/architecture/risk-register.md` | Architecture risks and mitigations |
| `docs/domain/product-requirements.md` | Requirements |
| `docs/domain/domain-models.md` | Salah, Sunnah, Nafl, Qaza, Fasting models |
| `docs/domain/qaza-accounting.md` | Append-only Qaza ledger and invariants |
| `docs/fiqh/rule-register.md` | Religious rules requiring scholarly verification |
| `docs/testing/strategy.md` | Test strategy |
| `docs/security/privacy-model.md` | Privacy and security model |
| `docs/decisions/` | Architecture Decision Records (ADR-0001 to ADR-0015) |

## Privacy notice for contributors

This repository is public. **Never commit personal worship data**, database files, backups or exports. See `docs/security/privacy-model.md` and `.gitignore`.
