# Privacy and Security Model

Status: **all controls below are PLANNED unless the table in section 7 says otherwise. As of Phase 0 nothing is implemented.**

## 1. Data classification

Worship records (missed prayers, Qaza balances, fasting history, reflections) are **highly sensitive personal data** about religious practice. They stay on the device.

## 2. Principles

- Local-only by default. No account, no sync, no ads, no analytics, no crash reporting by default.
- No `INTERNET` permission in the initial releases. Adding it (for any reason) requires an ADR and owner approval.
- Minimal permissions. Candidates and their gating: notifications (Phase 6, runtime permission), exact alarms (only if justified, with fallback), biometrics (Phase 8). Location is **not** requested; prayer-time location is entered manually or from a bundled city list unless an ADR approves otherwise.
- Logs never contain worship data or personal notes.

## 3. Public repository hygiene

The GitHub repository is public. Therefore:

- No database files, exports, backups, screenshots with real data, keystores or secrets in git. `.gitignore` blocks common patterns; CI (Phase 1) additionally fails on forbidden file types.
- Test fixtures use synthetic data only.
- Release signing material exists only as Actions secrets or on the owner's device.

## 4. Threat model (summary)

| Threat | Mitigation (planned) |
| --- | --- |
| Phone lost or borrowed | Optional app lock (biometric/device credential); notification text without sensitive detail on lock screen; optional screen-capture blocking |
| Other apps reading data | Private app storage; Android auto-backup and device transfer disabled or explicitly configured via data-extraction rules |
| Accidental repository leak | `.gitignore`, CI forbidden-file check, secret scanning, PR template checklist |
| Backup file theft | Encrypted backups with authenticated encryption; passphrase-derived key; versioned header (algorithms chosen and verified in Phase 8) |
| Data corruption / bug | Append-only ledger, atomic transactions, migration tests, restore verification |
| Supply-chain compromise | Pinned dependency versions, wrapper validation, pinned action SHAs |

## 5. Backup, restore, portability (Phase 8 design targets)

- Manual export in an open, documented format (JSON plus schema version) for portability.
- Encrypted backup file: authenticated encryption, key derived from a user passphrase with a memory-hard or iterated KDF, random salt/nonce, version header. Exact primitives and parameters decided in an ADR with sources.
- Restore is non-destructive by default: restore into a staging database, verify ledger invariants (balances, idempotency keys), then swap only on success, keeping the previous database until confirmed.
- Database-at-rest encryption (for example an encrypted SQLite build) is a Phase 8 decision with its trade-offs recorded; until then the app must not claim at-rest encryption.

## 6. App lock (Phase 8)

Uses the platform biometric/device-credential prompt. The lock protects UI access; it is not described as encryption unless database encryption is actually implemented.

## 7. Planned versus implemented

| Control | State |
| --- | --- |
| Local-only data | Planned (no code yet) |
| No INTERNET permission | Planned |
| `.gitignore` for sensitive files | **Implemented in repo (file exists); effectiveness not yet tested** |
| CI forbidden-file check | Planned (Phase 1) |
| App lock | Planned (Phase 8) |
| Encrypted backup | Planned (Phase 8) |
| Database encryption | Not decided |
| Auto-backup restrictions | Planned (Phase 1 manifest) |

## 8. Review

This document is re-audited at the end of Phase 8 and Phase 9, replacing every "Planned" with a verified state or removing the claim.
