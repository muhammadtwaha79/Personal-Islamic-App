# Owner requirements — Salawat, Istighfar, Bismillah and Dala'il

**Captured:** 2026-10-18. Status: OWNER-SUPPLIED PERSONAL WORSHIP TARGETS, **not** juristically imposed obligations or authenticated attributions.

## Daily recitation

- Short Salawat `اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ وَسَلِّمْ`: **1,200 repetitions per local calendar day**.
- Istighfar: **1,200 repetitions per day**. Text not specified by owner; UI offers brief `أَسْتَغْفِرُ اللَّهَ` only as a common display convenience, **not** an exclusive required wording.
- Bismillahir-Rahmanir-Rahim: **1,000 repetitions per day**. Display `بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ`.
- Increment and correct individual numbers; support +1, +10, +100 where appropriate. Counters persist offline across app restarts and don't assume recitation when no action is recorded.
- **The daily short-Salawat count is independent of the after-Fard 20-formulation routine**, avoiding implicit double counting. If the owner wants total Salawat aggregation across texts, implement an explicitly explained new projection later.

## After every Farz prayer

The owner supplied **20 formulations**: Salat al-Fatih, Jawharat al-Kamal, Tunajjina, Nariya, Shifa, Kamaliya, Imam Shafi'i-attributed, Sayyidina Ali-attributed, Kanz al-Asrar, Ibn Mashish, Azimiyya, In'amiyya, Tatimma, Nuraniyya, Wahida, Ghawth al-Azam-attributed, Fath al-Mubin (a separate user-supplied variant), Haqiqa, Kubra, and the special Chishti-associated prayer that closes with `الْفَاتِحَةُ`.

The phrase **"10x these salawats after every Farz namaz"** is implemented as a **personal default of 10 repetitions of EACH of the 20 texts, for EACH of the 5 Farz occurrences**: 100 distinct counters and a possible 1,000 after-Fard repetitions/day. This is not a religious requirement and no authentication or comparative potency is claimed. Counts are stored separately by formula, prayer, and local date; they are never automatically inferred from a Fard prayer completion.

The *Arabic text* is copied verbatim from the owner's message into `app/src/main/java/com/muhammadtwaha/ibadahcompanion/domain/dhikr/SalawatCatalog.kt` with stable identifiers. **The exact wording, naming and claimed attributions are not yet verified**; qualified textual and fiqh review is required before treating them as authoritative.

## Dala'il al-Khayrat

Track one daily **manzil** as a manually selected 1–7 number from the user's own edition, optional edition/schedule label, and checked completion. Different published editions have different manzil arrangement. **No canonical weekday mapping, religious virtue claim, or text reproduction is inferred.** Do not insert copyrighted edition text or force a particular day assignment without input.

## Data integrity

- Room v4, additive migration 3→4 from the existing v1→v2→v3 path.
- Append-only recitation events (signed adjustments), transactionally bounded to prevent negative counts and duplicate record ID conflict.
- Daily Dala'il selection is editable because it is a personal checklist; changing it must not change Salah, Qaza, Quran or other modules.
- No internet, analytics, credentials, or personal worship data in public GitHub.
- UI must show full Arabic, use RTL text direction and allow corrections.
- A green GitHub Actions build/test/lint and uploaded debug APK are engineering verification **only**; upgrade/migration on populated devices and UI/physical device reliability remain separate checks.

## Outstanding decisions

- Does the owner intend 10 repetitions of **all 20** after every Farz (1000/day), or a smaller personally selected subset? Implementation honors literal wording but this remains user-adjustable in a future preferences screen.
- Does Dala'il edition require prescribed weekly manzil mapping? The user must select the edition and manzil personally for now.
- Qualified expert review of wordings and historical attributions. Do not imply every listed wording is a Prophetic hadith.
- Future module: sourced morning/evening adhkar, custom Dhikr routines and personal Duas, scheduled after this counter MVP; keep Qaza/Fasting commitments on the roadmap.
