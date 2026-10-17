# ADR-0010: Identifiers: UUID plus ledger sequence

**Status:** Accepted

**Context.** Stable external ids are needed for idempotency, export/import and backup merges; ordering needs to be deterministic.

**Decision.** Entities use random UUIDs generated through an injected `IdGenerator`. The ledger additionally has a database-assigned monotonic `seq` for ordering. Idempotency keys are separate unique strings.

**Consequences.** UUIDs are not time-ordered; ordering relies on `seq` and timestamps, never on id.
