# Owner Brief — Salah, Sunnah/Nafl, Quran and Ramadan (18 Oct 2026)

**Status:** owner requirements; religious interpretations unverified, implementation must not invent fiqh or completion data.

## Effective personal tracking date

**20 October 2026**, locally dated. Record today or historical dates explicitly. Future completion recording is forbidden. Missing/unanswered is UNKNOWN, not automatically MISSED.

## Five daily Fard Salah

For each local prayer occurrence: Fajr, Dhuhr/Jumu'ah (Friday distinction), Asr, Maghrib, Isha. Independently record performance, congregational vs individual, joined from beginning vs late, and whether the user personally caught the **opening takbir (Takbeer-e-Ula)**. The relevant meaning and 40-day narrative require qualified scholarly review; app never guarantees particular religious reward.

**40-day personal challenge:** 20 Oct–28 Nov 2026 inclusive (40 days x 5 = 200 opportunities). Success counts strict confirmations only, pending/unknown never succeeds. Per-prayer metrics and consecutive complete-day metrics separate. Friday Jumu'ah is a midday occurrence variant with no unverified religious validity rules asserted. Track travel/illness excuses in a later religiously reviewed module; do not pretend a failed streak implies prayer invalidity.

## Sunnah and Nafl daily records

Track, without imposing automatic debt: Tahajjud, Ishraq, Chasht/Duha, Awwabeen; four before Asr, four before Isha (ghair muakkadah under preliminary Hanafi-oriented configuration); and familiar Fajr, Dhuhr, Maghrib, Isha Sunnah types. Individually capture completed/rakah count and permit corrections. Daily prayers are not the same as obligatory Qaza, which retains its planned append-only ledger architecture.

## Quran Tilawat

From **20 Oct 2026**: **half para / half juz daily** as the baseline; Surah Yaseen, Surah Waqiah and Surah Mulk as independent daily check-ins. Manual toggles, corrections, historical backfill, persistent local records. Quran text/religious merit claims are not included; this is a self-report tracker.

**Ramadan:** personal target **5 complete Qurans during one Ramadan**. A complete khatam means completing the full Quran, **not merely accumulating reading-volume equivalents**. Track both (a) half-juz reading volume and (b) user-confirmed completion events separately. Equivalent planned volume is 5 × 30 = 150 juz, i.e. 300 half-juz units, subject to user-confirmed Ramadan dates. Daily Ramadan pace dynamically depends on the confirmed duration (roughly 5 juz/day for 30 days). No date calculation or moon-sighting assertion is hard-coded. It must remain possible for other chapters to begin from different points; volume alone cannot certify completion of the whole Quran.

## Future modules (do not rush into this release)

- Morning/evening Adhkar, custom Dhikr and Salawat counters/plans.
- Dua library and personal dua lists; remembrance and consistency metrics.
- Scholar-verified wording/sourcing for transmitted adhkar; no fabricated virtue claims.
- Fasting plan and Qaza engine from the existing roadmap must not be forgotten.

## Engineering acceptance and boundaries

- Local Room database with additive migrations and corrections.
- App remains offline-first; no INTERNET permission, no analytics.
- Room persistence, reliable date boundaries, all four navigation screens.
- Reproducible CI: compile, unit tests, Android lint and debug APK upload.
- No personal records, Quran-reading history or worship datasets in public GitHub repository.
- New Islamic claims go into the fiqh rule register as UNVERIFIED pending review.
- Ramadan start/end selected by the user; never claim the chosen dates correspond to moon sightings.
