# ADR-0018 — Offline personal Salawat recitation accounting

**Status:** Accepted as an engineering model (2026-10-18), **not** approval of religious claims.

## Context

Owner requested short Salawat 1,200/day, Istighfar 1,200/day, Bismillah 1,000/day, 10 repetitions of each of 20 supplied Salawat formulations after each Farz prayer, and a Dala'il al-Khayrat daily manzil. No previous Dhikr data exists in the Room v3 schema.

## Decision

1. Full owner-provided Arabic texts are static read-only catalog data with stable IDs. Catalog labels and attributions are **owner-supplied, UNVERIFIED**, and not evidence of source authenticity.
2. Counter events are append-only with UUID, local ISO date, practice ID, prayer scope (empty for daily targets), signed increment, and recorded timestamp. Derived counts use SQL `SUM(delta)`. Transactions validate count remains nonnegative and under a safety maximum; no REPLACE / destructive migration.
3. The per-Fard scope does **not** double count into the independent daily 1,200 short Salawat counter; separate projections can be introduced later.
4. Dala'il is a manually labeled and checked `manzil 1..7` per local day, with optional edition label. No book content or invented weekday mapping is shipped.
5. Room schema v4 introduces `recitation_event` and `dalail_daily` with **additive** 3→4 migration. Older v1–v3 schema chain remains supported.
6. Date-based UI blocks future completion. The user can correct past entries with negative deltas, never below zero. No automatic obligation is created from targets not met.

## Risks and evidence

- Real-device database migration and UI tests must still be added; a successful JVM CI run does not replace them.
- No encrypted backup exists yet: device data loss remains a significant limitation.
- Long Arabic texts require qualified letter-by-letter review. Source authentication and fiqh considerations must be maintained in the register before offering sourced claims.
- Personal goals of 1,200/1,000 and 10x are chosen by the owner, NOT a universal Sunnah or Wajib ruling.

Verification evidence: see [ENGINEERING_STATUS](../ENGINEERING_STATUS.md) and the Dhikr feature PR.
