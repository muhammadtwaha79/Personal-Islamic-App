# Domain Models (design)

Language-neutral design. Names are provisional until Phase 1. All domain types live in pure Kotlin (no Android imports). Persistence mappings come in later phases.

## Common concepts

- `Prayer`: `FAJR, DHUHR, ASR, MAGHRIB, ISHA`.
- `FiqhProfile`: `HANAFI` initially. Others added only through the rule register. Profile must be user-confirmed; stored with confirmation timestamp and register version.
- Time model (ADR-0008): instants in UTC, plus IANA zone id when local meaning matters; *occurrence date* is a local civil date string (`yyyy-MM-dd`) of the prayer's day, never derived later from an instant.
- Identifiers: random UUIDs (ADR-0010).

## Salah (Fard)

`PrayerRecord` (current state) with immutable `PrayerRecordRevision` history:

| Field | Notes |
| --- | --- |
| id | UUID |
| prayer | enum |
| occurrenceDate | local date |
| status | `PENDING, COMPLETED_PREFERRED, COMPLETED_LATE_IN_WINDOW, MISSED, COMPLETED_AS_QAZA, UNKNOWN, EXCUSED` |
| windowStart / windowEnd | UTC instants, with `TimeCalcSnapshot` (method, Asr juristic setting, location label, adjustments) |
| preferredEnd | optional instant; meaning defined by register rule |
| completedAt | nullable instant; user-asserted |
| createdAt | instant, set once |
| lastCorrectedAt | nullable instant |
| excuseNote | nullable text (no automatic Qaza waiver; see register) |
| revisionNo | integer |

Derived (never stored): `NEEDS_REVIEW` = `PENDING` and `now > windowEnd`.

Revision rows record previous status, new status, reason, timestamp, and (if applicable) the ledger entry ids created or reversed in the same transaction.

## Sunnah and Nafl

`SunnahRuleSet` is data derived from the register for a profile: for each `Prayer`, entries of `{position: BEFORE|AFTER, rakat, classification}` where classification is `MUAKKADAH, GHAIR_MUAKKADAH, NAFL_OPTIONAL, WITR_WAJIB`. Classification values are *register data*, not code constants.

`VoluntaryPrayerType`: `TAHAJJUD, ISHRAQ, DUHA, AWWABEEN, TAWBAH, TAHIYYAT_WUDU, TAHIYYAT_MASJID, GENERAL_NAFL`.

`VoluntaryPrayerLog { id, type | sunnahSlot, occurrenceDate, rakatPerformed, completedAt, note, createdAt }`. User-configurable target rakat per type (`VoluntaryPrayerConfig`, versioned). No Qaza linkage.

## Qaza and obligations

See `qaza-accounting.md`. Core types: `ObligationKind` (`SALAH_FARD, SALAH_WITR, RAMADAN_FAST`), `ObligationCategory` (prayer or fast), `LedgerEntry` (immutable), `RecoveryPlan` (mutable configuration, not authoritative for balances).

## Fasting

- `FastRecord { id, date (Gregorian local), hijriDateAtCreation (snapshot with calendar config), type: RAMADAN_FARD | QAZA | VOLUNTARY | OTHER, voluntaryKind?, status: PLANNED | COMPLETED | MISSED | BROKEN | EXCUSED | UNKNOWN, note, createdAt, lastCorrectedAt }`.
- Ramadan misses that the user designates as owed append to the shared obligation ledger (`RAMADAN_FAST`).
- `HijriCalendarConfig { method, dayOffset (global), monthOverrides[] }`: user-adjustable because local sighting may differ from calculation.
- `FastingPlan { id, kind, params, activeFrom, activeTo }` (voluntary schedules; mutable configuration).
- Broken voluntary fasts: recorded as `BROKEN`; **no** automatic Qaza created until rule FQ-022 is verified.
- No Kaffarah entity exists in v1 by design.

## Invariants shared across the domain

1. History is never destroyed: edits create revisions; ledger entries are immutable.
2. Estimates stay estimates: they carry a `certainty` marker and never produce dated prayer records.
3. Religious classification is data from the register, versioned, never hard-coded in UI.
