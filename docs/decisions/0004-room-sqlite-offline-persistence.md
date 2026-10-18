# ADR-0004: Room over SQLite, offline-only persistence

**Status:** Accepted

**Context.** Records are sensitive and must survive failures; transactions and triggers are needed for ledger invariants.

**Decision.** Room over SQLite is the system of record. Exported schemas are committed. Triggers (created in migrations) enforce ledger append-only and non-negative balance. No network storage, no sync.

**Consequences.** Triggers must be tested on a real SQLite engine. Raw SQL for triggers lives in versioned migration code. Cloud backup is out of scope; Phase 8 provides manual encrypted backup.
