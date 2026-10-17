# ADR-0005: Append-only Qaza ledger

**Status:** Accepted (owner requirement)

**Context.** Obligations must never disappear through bugs, double-taps or edits. An editable counter cannot be audited.

**Decision.** Balances derive solely from immutable ledger entries (`docs/domain/qaza-accounting.md`). Corrections are reversals plus new entries. UPDATE and DELETE are rejected by database triggers. Balance non-negativity and idempotency are enforced in the domain and in the database.

**Consequences.** More rows and slightly more complex reads (sums). Eliminates a class of silent corruption. A cached balance may exist only as a disposable, verified optimization.
