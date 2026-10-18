# ADR-0015: Disputed conventions are configurable; no silent defaults

**Status:** Accepted (owner decisions, 2026-10-17)

**Context.** Several practices have disputed conventions (Asr calculation, order of Qaza, Witr status). Owner direction: do not select silently, do not enforce disputed ordering, do not block engineering on open religious questions.

**Decision.**
1. **Asr:** the calculation convention is an explicit user setting. No default is silently selected. Until the user chooses, prayer windows are shown as "setup needed" rather than computed with a hidden convention (details in Phase 2).
2. **Qaza ordering:** the app does not enforce any ordering of Qaza (rule FQ-004 stays `UNVERIFIED`; completions are order-agnostic).
3. **Witr:** a separate, configurable obligation category. It is never added automatically; enabling it requires explicit user confirmation recorded with a timestamp and the active profile (FQ-003).
4. **Qaza estimates:** the app supports the estimated 365 occurrences per Fard category as an editable suggestion offered at setup; nothing is written to the ledger until the user confirms. Estimates can be corrected manually at any time by appending correction entries.
5. Other open religious questions do not delay engineering; affected features ship configurable or inert.

**Consequences.** Onboarding has extra explicit choices. Code never hard-codes disputed rulings. The suggested 365 figures remain the owner's planning estimates, not verified records.
