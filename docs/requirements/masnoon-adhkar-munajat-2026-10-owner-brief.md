# Owner brief — Morning/Evening Azkar, Daily Masnoon Duas/Sunnah and Munajat-e-Maqbool

Date: **2026-10-18**. This is a self-improvement program, not a machine-generated fatwa or an exhaustive religious canon.

## Owner goals

- Build an educational personal companion for learning and consistently following Prophet Muhammad's ﷺ authentic daily practices, with descriptions and trustworthy source references.
- Morning and evening remembrance as separate independently completed schedules: **Sayyidul Istighfar**, **Surah al-Ikhlas/al-Falaq/an-Nas three times EACH**, morning/evening invocations, **Bismillahilladhi la yadurru 3x**, **Subhanallahi wa bihamdihi 100x**, protection/well-being prayers, and the owner's specifically requested **Hasbiyallahu 7x**.
- Daily life supplications and Sunnah: waking/sleeping, using toilet, leaving home, entering/leaving mosque, food, Adhan, post-Fard dhikr, greeting, eating etiquette, siwak, footwear, and other source-verified contextual habits.
- *Munajat-e-Maqbool*: **one manzil per day, manual 1–7 selector, optional edition/schedule label, completion/undo only** — do NOT embed or assume rights to reproduce the book text.
- Existing *Dala'il al-Khayrat* independent manzil tracker remains unchanged; do not conflate the two books.

## Initial curated content, not an exhaustive claim

This PR implements **28** source-linked dua/dhikr entries covering morning/evening/occasional/after-Salah contexts, plus **10** context-dependent Sunnah behavior check-ins. The collection can grow by independently verifying new sources and wordings. Do not label it “ALL Ahadith” or “entire Sunnah” because that would be inaccurate.

The three short Quran chapters are tracked as **three complete recitations of each surah per morning AND evening**, not as reading merely their titles. The screen points to the Mushaf rather than pretending its title is the full surah text.

**Critical evidence caution:** Sunan Abi Dawud 5081 contains the report mentioning seven morning/evening recitations of `حَسْبِيَ اللَّهُ لَا إِلَهَ إِلَّا هُوَ ...`, but the viewed listing grades it **Mawdu' (fabricated) (Al-Albani)**. It is included **ONLY as an opt-in, clearly DISPUTED user-requested optional practice**, outside the authenticated core completion numerator/denominator. Never advertise the alleged guaranteed protection as established from the Prophet ﷺ.

Some additional collections/rulings may have disagreements on grading or specific recitation times; preserve the source attribution and obtain qualified scholarly review before teaching a contested grade as unanimous.

## Engineering design

- Existing Room `recitation_event` append-only signed-delta journal is reused for dua counters; scope codes `MORNING`, `EVENING`, `OCCASION` and `AFTER_SALAH_<PRAYER>` keep distinct contexts independent.
- A single canonical name/ID for a dua across its supported windows. Date and slot drive separate totals; NEVER count morning as evening, or after-Salah in the 1,200 personal Istighfar counter.
- `daily_sunnah_check` stores **self-reported once-per-day practiced** status, not one row per lifetime occurrence nor proof of obligation; contextual acts cannot be missed when there was no opportunity.
- `munajat_manzil` stores daily manzil, optional edition string and completion; user chooses the division, no automatic assumed day mapping.
- Room schema **v5**, explicit *additive migration 4→5*, no destructive migration, no INTERNET permission, no analytics, no personal data in public GitHub.
- UI displays original Arabic, hadith reference, source URL as copyable text, reported grading category and editorial caveat; corrections require an existing positive balance.
- Future logs prohibited; tracking dates are user-selected civil dates, no automated calculation of Islamic calendar or obligatory status.
- Event timestamps show when a user records an action, **not proof the act happened at that moment**.

## Remaining to be completed after MVP

- Proper Arabic diacritics / manuscript variant checking on long duas, named-authority grade review, complete daily Sunnah catalog inventory and useful Roman Urdu meanings/transliterations, guided learning.
- One-time practices may happen several times a day; the current once-per-date totals are self-reported counts, not a full episode diary or time-based scheduler.
- Verified Android emulator or physical device smoke testing, populated Room migration 1→2→3→4→5 integration test, app access controls, safe encrypted export/restore.
- Qaza ledger, fasting and other previously approved roadmap modules MUST remain visible.

## References and review register

See `docs/research/masnoon-source-register.md`, `docs/fiqh/rule-register.md`, and ADR-0019. Source pages checked 2026-10-18. Exact Arabic letters and grading can differ between editions; don't conflate source existence with final authentication.
