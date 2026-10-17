# ADR-0006: Shared obligation ledger for Salah and fasting

**Status:** Accepted

**Context.** Fasting Qaza has the same accounting needs as prayer Qaza.

**Decision.** One ledger engine keyed by `(obligation_kind, category)`. Kinds: `SALAH_FARD`, `SALAH_WITR`, `RAMADAN_FAST`. Balance and invariants apply per key. Phase 4 delivers Salah; Phase 5 adds fasting without schema redesign.

**Consequences.** One audited implementation. The `kind` and `category` enums must be extended with migrations that never change the meaning of existing rows.
