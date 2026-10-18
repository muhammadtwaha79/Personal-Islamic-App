# Masnoon Azkar & Sunnah — Source Review Register

**Initial source browse:** 2026-10-18. Sources are linked in the app in `MasnoonCatalog.kt`. This document records the looked-up hadith collection/text and reported grading; the app is **not** an independent hadith scholar, and all longer Arabic text needs qualified editorial review before any formal authenticity claim.

| Practice / group | Source | Reported status / implementation |
| --- | --- | --- |
| Sayyidul Istighfar day/night | [Sahih al-Bukhari 6306](https://sunnah.com/bukhari:6306) | Sahih collection; separately log morning and evening personal check-ins |
| Three Qul Surahs each 3x morning/evening | [Sunan Abi Dawud 5082](https://sunnah.com/abudawud:5082) | Listed Hasan (al-Albani); do not count chapter names as the full recitation |
| Bismillahilladhi la yadurru 3x | [Sunan Abi Dawud 5088](https://sunnah.com/abudawud:5088) | Listed Sahih (al-Albani) |
| Subhanallahi wa bihamdihi 100x morning/evening | [Sahih Muslim 2692](https://sunnah.com/muslim:2692) | Sahih collection |
| Allahumma bika asbahna / bika amsayna | [Sunan Abi Dawud 5068](https://sunnah.com/abudawud:5068) | Listed Sahih (al-Albani) |
| Al-'afw wal-'afiyah morning/evening | [Sunan Abi Dawud 5074](https://sunnah.com/abudawud:5074) | Listed Sahih (al-Albani); manuscript textual variations need editorial review |
| Allahumma afini fi badani 3x | [Sunan Abi Dawud 5090](https://sunnah.com/abudawud:5090) | Listed Hasan in chain (al-Albani); part of a longer narrated supplication |
| **Hasbiyallahu seven times** | [Sunan Abi Dawud 5081](https://sunnah.com/abudawud:5081) | **Mawdu' / fabricated by al-Albani on the cited listing. Optional requested practice only; do NOT advertise as reliably Prophetic or guarantee a reward**. Scholarly-grade differences may exist; independent review required |
| Sleeping and waking duas | [Sahih al-Bukhari 6324](https://sunnah.com/bukhari:6324) | Sahih |
| Toilet entry | [Sahih al-Bukhari 142](https://sunnah.com/bukhari:142) | Sahih |
| Toilet exit | [Sunan Abi Dawud 30](https://sunnah.com/abudawud:30) | Listed Sahih (al-Albani) |
| Leaving home | [Sunan Abi Dawud 5095](https://sunnah.com/abudawud:5095) | Listed Sahih (al-Albani) |
| Mosque entry/exit | [Sahih Muslim 713a](https://sunnah.com/muslim:713a) | Sahih |
| After Adhan | [Sahih al-Bukhari 614](https://sunnah.com/bukhari:614) | Sahih |
| Start eating, eat with right hand, take from what is near | [Sahih al-Bukhari 5376](https://sunnah.com/bukhari:5376) | Sahih |
| Post-Salah istighfar 3x, Allahumma antas-salam | [Sahih Muslim 591](https://sunnah.com/muslim:591) | Sahih |
| Post-Salah Tasbih/Tahmid/Takbir 33 each, concluding Tahlil 1 | [Sahih Muslim 597a](https://sunnah.com/muslim:597a) | Sahih; this is one reported pattern, not an exclusive valid sequence |
| Siwak when waking at night | [Sahih al-Bukhari 245](https://sunnah.com/bukhari:245) | Sahih, specific occasion |
| Wudu before bed | [Sahih al-Bukhari 247](https://sunnah.com/bukhari:247) | Sahih, contextual |
| Sleeping on right side | [Sahih al-Bukhari 6315](https://sunnah.com/bukhari:6315) | Sahih, contextual |
| Shoes: put right on first / remove left first | [Sahih al-Bukhari 5855](https://sunnah.com/bukhari:5855) | Sahih |
| Preference for starting on right | [Sahih al-Bukhari 168](https://sunnah.com/bukhari:168) | Sahih, context dependent |
| Salam/greeting and feeding others | [Sahih al-Bukhari 12](https://sunnah.com/bukhari:12) | Sahih, opportunity dependent |
| Sneezing etiquette | [Sahih al-Bukhari 6224](https://sunnah.com/bukhari:6224) | Sahih |

## Evidence categories in code

- `SAHIH`: named source from Sahih al-Bukhari or Sahih Muslim; **not** a separate audit of every translation or diacritic.
- `SAHIH_GRADED`, `HASAN`, `HASAN_CHAIN`: the cited listing's reported scholar grade, not an independent consensus.
- `DISPUTED`: specifically flagged lower-confidence/criticized reports, **excluded from the standard completed-goals numerator**. No claim about specific blessing is displayed.
- Textual and matn-level professional source review still pending, especially longer Arabic passages; varying editions can differ. *A link to a source alone does not guarantee the transcription was copied perfectly.*

## Editorial expansion policy

To add any new "masnoon" entry: confirm accessible original hadith source, exact Arabic, transmission/grading in named editions, context and authentic repetition count. Clearly distinguish obligatory status, Sunnah etiquette and user-specific optional routines. Do not add 200 uncertain duas to meet an artificial “everything” target. There is no claim this initial catalog is exhaustive.
