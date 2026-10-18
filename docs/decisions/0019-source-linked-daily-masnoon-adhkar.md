# ADR-0019 — Source-linked Daily Adhkar and Munajat tracking

**Status:** ACCEPTED for engineering design, NOT a universal religious or textual certification. Date: 2026-10-18.

## Context

Owner requested comprehensive daily practice learning: morning and evening adhkar (different counts, e.g., 3x Qul Surahs, Sayyidul Istighfar, 7x Hasbiyallahu), authentic daily life duas and Sunnah etiquette, and a manual daily *Munajat-e-Maqbool* manzil alongside existing *Dala'il al-Khayrat* manzil. Prior app has Room v4, signed recitation events and separate Dala'il records. Arabic texts and source grading require care and correction.

## Decision

1. Ship a *curated, source-linked starter collection*, not an unsupported claim that the entire Sunnah is covered. Catalog IDs remain stable. Source URL, book/hadith number, reported grade, Arabic body and context note are stored with each item.
2. Morning/evening recitations have separate event scopes `MORNING` and `EVENING`. Occasion recitations use `OCCASION` per date; post-Salah recitations use `AFTER_SALAH_<FAJR|DHUHR|ASR|MAGHRIB|ISHA>`. All reuse the nonnegative append-only `recitation_event` counter ledger. No implicit attribution to existing Salawat 1,200/1,200/1,000 goals.
3. Three Qul Surahs are independent 3x each in each window (six daily independent goals, 18 total full-surah recitations if all reached); the title is a prompt to read the full Surah from the Mushaf, NOT its complete text.
4. `Hasbiyallahu 7x` is only an explicitly flagged optional entry. The cited Sunan Abi Dawud 5081 listing gives `Mawdu'` (al-Albani) and it is **excluded from ordinary completion calculations**, despite the owner's desire to track it. Do not promise any specific virtue.
5. Contextual daily habits are once-per-date *self-reports*, with no fabricated “missed Sunnah” or binding obligation if circumstances did not arise.
6. *Munajat-e-Maqbool* daily manzil is manually chosen (1..7 plus optional edition), separately stored from *Dala'il al-Khayrat*. Do not assume a canonical weekly division, include book text or imply usage rights.
7. Room schema v5 includes two additive tables `daily_sunnah_check`, `munajat_manzil`; migration 4→5 must not alter v1–4 data. Existing Android CI and builds must continue.
8. UI displays Arabic RTL, author/book reference and readable source URL and clearly labels unverified/disputed textual matters. No network permission is introduced.
9. No psychology/"mind programming" claim of guaranteed behavioral modification or spiritual rank; maintain gentle user-controlled reminders and education. The current feature needs real-device and migration tests before sole-record use.

## Alternatives rejected

- Auto-marking practices completed at morning/evening time;
- Labeling all 7x claims as authenticated hadith;
- Auto selecting edition-specific Munajat manzil;
- Dumping massive unverified Arabic texts or copying published book content;
- Replacing (rather than migrating) the existing private worship database.

## Quality gates

CI compile, JVM tests, Android lint, APK upload, manual device smoke test, source review of Arabic spellings and provenance, and eventual instrumented populated v4→v5 migration. Only first four may be available immediately; report others as pending.
