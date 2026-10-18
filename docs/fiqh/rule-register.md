# Fiqh Rule Register

Register version: **0.1.1 (draft)**. Date: 2026-10-17.

> **Read this first.** Every rule below is **UNVERIFIED**. The author (an AI engineering assistant) wrote them from general knowledge to define *what must be decided*, not to state what is correct. No rule here has been checked by a qualified scholar. No Quran or Hadith reference is given because none has been verified; none will be added from memory. The app is a tracking tool, not a source of fatwas.

## Verification statuses

- `UNVERIFIED`: written down for review; do not rely on it. Implementation must stay configurable or inert.
- `SOURCES_PROPOSED`: candidate primary and scholarly sources identified, not yet reviewed.
- `VERIFIED`: reviewed by a named qualified reviewer, with citations recorded.
- `DISPUTED`: scholars differ; the app exposes a user-confirmed setting rather than a silent default.

## Review process

1. The owner nominates a qualified reviewer (for example a Hanafi mufti or a recognized dar al-ifta).
2. For each rule: record the ruling, citations (book, chapter/section, edition) or the written fatwa reference, reviewer, date.
3. Update status and version. Code must read rules from this register's data, never hard-code them.
4. Any change to a verified rule bumps the register version; records snapshot the version they used.

## Initial jurisprudential profile

Hanafi (owner-approved, ADR-0007). The user must explicitly confirm the profile in onboarding; the app does not assume the same rulings for other schools. Other schools' positions below are recorded only as *known areas of difference to be documented after review*.

## Owner decisions recorded 2026-10-17 (engineering policy, not religious verification)

These decisions govern how the app behaves while rules remain open. They do **not** change any rule's status, which stays `UNVERIFIED`.

- Keep all unverified rules marked `UNVERIFIED`; do not invent references.
- Qaza: support the estimated 365 per Fard category; Witr is a separate configurable category, never added without user confirmation; manual estimate corrections are supported; disputed Qaza ordering is not enforced.
- Asr: configurable calculation convention; never silently selected.
- Open religious questions must not delay the Android engineering foundation.

See ADR-0015.

## Rules

Each rule: **Practice / School / Ruling as currently understood (unverified) / Source / Status / Known differences / Implementation implication.**

### FQ-001 Rakah counts of the five Fard prayers
- Practice: Fajr 2, Dhuhr 4, Asr 4, Maghrib 3, Isha 4. School: all (commonly stated as agreed).
- Source: to be cited. Status: `UNVERIFIED`.
- Differences: travel (qasr) and other situational changes exist and are out of v1 scope.
- Implication: fixed display data; qasr not modelled in v1 (flag in UI copy, not logic).

### FQ-002 Sunnah and Nafl classification (Hanafi initial table)
- Ruling as supplied by the owner and currently understood: Fajr 2 Muakkadah before Fard; Dhuhr 4 Muakkadah before, 2 Muakkadah after, 2 optional Nafl after; Asr 4 Ghair Muakkadah before; Maghrib 2 Muakkadah after (plus optional Nafl); Isha 4 Ghair Muakkadah before, 2 Muakkadah after (plus optional Nafl).
- Source: to be cited. Status: `UNVERIFIED`.
- Differences: schools differ on which rawatib are emphasized, counts, and classification terminology; even within Hanafi texts, presentation can vary.
- Implication: stored as a profile-specific data table (`SunnahRuleSet`); app shows no table until a profile is confirmed.

### FQ-003 Witr and its Qaza
- Ruling as currently understood: under the Hanafi framework Witr (3 rakah) is Wajib, and a missed Witr requires Qaza. Other schools generally classify Witr as a confirmed Sunnah without Qaza obligation in the same sense.
- Source: to be cited. Status: `DISPUTED` across schools; Hanafi position `UNVERIFIED`.
- **Owner decision (2026-10-17):** Witr is a **separate, configurable category**. It is **never added automatically**; enabling it requires explicit user confirmation, recorded with timestamp and active profile. The planning figure of 365 Witr is only an editable suggestion offered after enabling.
- Implication: Witr ledger entries are accepted only while an active confirmation exists (invariant L11 in `qaza-accounting.md`). Switching profile never deletes ledger entries; each entry records the profile and register version in force when it was created.

### FQ-004 Order of making up missed prayers (tartib)
- Ruling as currently understood: Hanafi texts discuss maintaining order between missed and current prayers, with conditions under which the requirement lapses. Details (thresholds, forgetfulness, time constraints) unknown to the author.
- Source: to be cited. Status: `UNVERIFIED` / `DISPUTED`.
- **Owner decision (2026-10-17):** disputed Qaza ordering rules are **not automatically enforced**.
- Implication: completions are order-agnostic; the UI will not claim any order is correct or required.

### FQ-005 Prayer-window boundaries and Asr start
- Open questions: Asr start (one shadow-length vs two, commonly associated with different schools), the end of Fajr at sunrise, Dhuhr beginning after zenith, Maghrib end / Isha start (twilight definition: red vs white shafaq), Fajr and Isha twilight angle conventions.
- Status: `DISPUTED` / `UNVERIFIED`.
- **Owner decision (2026-10-17):** Asr calculation convention is **configurable and never silently selected**.
- Implication: calculation method, Asr setting and twilight convention are explicit user settings with no silent default; until chosen, windows show "setup needed". The settings used are snapshotted on every record (`TimeCalcSnapshot`).

### FQ-006 "Preferred time" versus end of window
- Open question: what counts as the preferred (mustahab) portion for each prayer (for example early Fajr versus sunrise approach, Asr before the sun's discoloration, avoiding delay of Maghrib).
- Status: `UNVERIFIED`.
- Implication: the state `COMPLETED_LATE_IN_WINDOW` depends on this. Until verified, `preferredEnd` is optional and the app can simply use `COMPLETED` semantics without the preferred/late split.

### FQ-007 Tahajjud boundaries
- Open questions: starts after Isha vs requires prior sleep; ends at true dawn; recommended portion of the night.
- Status: `DISPUTED` / `UNVERIFIED`. Implication: configurable start rule; no guidance text until verified.

### FQ-008 Ishraq timing
- Ruling as currently understood: prayed after sunrise once the forbidden time has passed; a commonly quoted delay after sunrise is mentioned in popular sources but the exact figure is unknown to the author.
- Status: `UNVERIFIED`. Implication: user-configurable minutes-after-sunrise with no number presented as a ruling.

### FQ-009 Duha / Chasht timing and rakah counts
- Open questions: window (after Ishraq period until before zenith with a safety margin), minimum and maximum rakah, relationship to Ishraq.
- Status: `UNVERIFIED`. Implication: configurable rakah; window from register once verified.

### FQ-010 Awwabeen interpretations
- Known difference: some identify Awwabeen with a set of rakah after Maghrib; others identify it with the Duha period. The author cannot state which is authoritative.
- Status: `DISPUTED`. Implication: the user selects an interpretation (or tracks it as general Nafl); the app does not prefer one silently.

### FQ-011 Salat al-Tawbah, Tahiyyat al-Wudu, Tahiyyat al-Masjid
- Open questions: rakah count, conditions, applicability at disliked times, and strength of the supporting reports (requires hadith-grading review, not done).
- Status: `UNVERIFIED`. Implication: general-Nafl-style tracking with configurable rakah; no virtue claims in copy.

### FQ-012 Disliked and forbidden prayer times
- Open question: exact intervals around sunrise, zenith and sunset; which prayers are allowed in them (for example the same-day Fard Asr at sunset, Janazah, Qaza) under the Hanafi view.
- Status: `UNVERIFIED`.
- Implication: the UI may *warn* using register data but must not block recording of a prayer the user says they prayed.

### FQ-013 Qaza of missed voluntary and Sunnah prayers
- Ruling as currently understood: missed voluntary prayers generally create no Qaza obligation; certain Sunnah (for example Sunnah of Fajr) have specific make-up rules in some texts.
- Status: `UNVERIFIED`. Implication: no Qaza creation for any voluntary type in v1 (FR-SUN-005); special cases are only added after verification.

### FQ-014 Estimating historical Qaza
- Open questions: from when obligations began, how to count when unsure, whether a conservative estimate is expected, treatment of periods of uncertainty.
- Status: `UNVERIFIED`.
- Implication: **the app records the user's own number and never computes or fabricates it.** The planning figure of 365 per prayer is the owner's estimate, not a ruling; the app supports it as an editable suggestion and supports manual corrections (ADR-0015). The owner should seek qualified guidance on the estimate.

### FQ-015 Fasting: Ramadan Qaza and Kaffarah
- Ruling as currently understood: a missed Ramadan fast is made up later (Qaza). Kaffarah applies only to specific deliberate violations with conditions that differ across schools.
- Status: `DISPUTED` for Kaffarah details; `UNVERIFIED`. Implication: the app never imposes Kaffarah and has no Kaffarah entity (FR-FST-006).

### FQ-016 Days on which fasting is restricted
- Open questions: the two Eid days, the days of Tashriq (11-13 Dhul Hijjah), fasting Friday alone, fasting only on Saturday, doubtful day (Yawm al-Shakk). Strength of restriction (forbidden vs disliked) differs by school.
- Status: `DISPUTED` / `UNVERIFIED`. Implication: warnings from register data, wording reflects uncertainty until verified; plans exclude Eid days by default.

### FQ-017 White days (13th, 14th, 15th Hijri)
- Status: `UNVERIFIED`. Implication: date logic depends on the Hijri calendar setting and local sighting (FQ-021); the app labels dates as "according to your calendar settings".

### FQ-018 Six days of Shawwal
- Open questions: whether consecutive days or immediately after Eid is required or preferable; historical differences in views.
- Status: `DISPUTED` / `UNVERIFIED`. Implication: configurable plan without a preferred pattern pushed by the app.

### FQ-019 Day of Arafah
- Open questions: which day (local sighting vs Makkah's Arafah date), non-pilgrim vs pilgrim.
- Status: `UNVERIFIED`. Implication: user-selectable date reference; defaults to the app's Hijri setting with an explicit note.

### FQ-020 Ashura and Muharram fasting
- Open questions: 10th alone vs with the 9th or 11th, strength of each recommendation.
- Status: `DISPUTED` / `UNVERIFIED`. Implication: plan options, no single option presented as the ruling.

### FQ-021 Hijri month start and local adjustment
- Open question: calculation vs local moon sighting; which authority the user follows.
- Status: `DISPUTED`.
- **Owner decision (2026-10-17):** configurable Hijri calendar with local date adjustment and monthly overrides; **no calculation algorithm selected** without proper evaluation (ADR-0013).
- Implication: never claim calculated dates equal sighting.

### FQ-022 Breaking a voluntary fast
- Ruling as currently understood: schools differ on whether a voluntary fast that is broken must be made up. The Hanafi position is commonly stated as requiring a make-up; other schools differ.
- Status: `DISPUTED` / `UNVERIFIED`.
- Implication: broken voluntary fasts are recorded as `BROKEN` only; **no automatic Qaza** until reviewed and the user confirms the policy.

### FQ-023 Excuses (illness, travel) and unable-to-fast cases
- Open questions: when an excuse defers vs waives the obligation, and the concept of compensation (fidya) in permanent inability.
- Status: `UNVERIFIED`. Implication: `EXCUSED` is a recorded status only; it never silently cancels an obligation or creates one.

### FQ-024 Salawat texts, names and attributions (personal catalog)
- Owner supplied 20 named Arabic formulations, including a separate short form for daily 1,200 recitations. Original names and source attributions require *individual* verification against qualified scholars and primary texts. No hadith authenticity, attribution, comparative potency, or guaranteed spiritual reward is asserted.
- **Status: `UNVERIFIED`**. Implementation: store supplied Arabic as owner-submitted personal reading material; show caveat in UI, not a sourced fatwa.

### FQ-025 Repetition targets after Fard
- Owner's stated routine: 10 repetitions of each of the 20 supplied formulations after each Farz prayer, independent of the separate 1,200 short-form Salawat goal.
- **Status: `USER-PERSONAL-GOAL` for schedule; `UNVERIFIED` as a religious prescription.** No automatic link to the valid performance of a Fard prayer, and no claim that a fixed 10x is universal Sunnah.

### FQ-026 Istighfar 1,200 and Bismillah 1,000 daily
- These are user-chosen daily practice amounts. The brief Istighfar example is not imposed as the one uniquely prescribed wording.
- **Status: `USER-PERSONAL-GOAL` for quantity; `UNVERIFIED` for any specific attributed virtue.** Do not require qaza of missed personal goals.

### FQ-027 Dala'il al-Khayrat weekly manzil
- Editions differ in manzil partition and labeling. Owner chooses a 1–7 number and optional edition each day; the program tracks completion only and reproduces no edition text.
- **Status: `UNVERIFIED` for canonical day-to-manzil attribution or specific virtues.** Do not silently select a schedule.

### FQ-028 Authentic morning/evening Adhkar, variation and repetition
- The initial collection includes three Qul Surahs 3x morning/evening (Sunan Abi Dawud 5082, graded Hasan by al-Albani on the listing), Sayyidul Istighfar by day/night (Sahih al-Bukhari 6306), the Bismillahilladhi la yadurru 3x (Abu Dawud 5088), and Subhanallahi wa bihamdihi 100x morning/evening (Sahih Muslim 2692). Each reported pattern has its own timing and frequency; mixing them, claiming universal grades or silently changing counts is prohibited.
- **Status: `SOURCE-LINKED / SCHOLAR-REVIEW-PENDING`.** Primary collection references have been located, but longer Arabic wording/diacritics and fiqh contexts await editorial validation. See `docs/research/masnoon-source-register.md`.

### FQ-029 Sevenfold Hasbiyallahu report
- Owner requested `حَسْبِيَ اللَّهُ لَا إِلَهَ إِلَّا هُوَ ...` 7x morning/evening. Sunan Abi Dawud 5081's cited listing explicitly grades the particular report **`Mawdu' (fabricated) (Al-Albani)`**.
- **Status: `CONTESTED / DO NOT AUTHENTICATE`.** Display only with visible grade warning and optional self-report, EXCLUDED from the core masnoon-completion count. Never teach a fabricated-grade report as universally authenticated Sunnah, and never claim a guaranteed reward. Other assessments require separate documented review.

### FQ-030 Daily Sunnah etiquette and occasion-dependent duas
- Source-linked entries exist for food etiquette, wudu before sleep, right-side sleep, greetings, siwak on waking at night, toilet entry/exit, mosque entry/exit, after Adhan, and one post-Salah remembrance pattern. These are context-dependent; the absence of an opportunity MUST NOT count as moral or religious failure.
- **Status: `SOURCE-LINKED / SCHOLAR-REVIEW-PENDING`.** The current app is a curated educational starting set, not a complete enumeration of the Sunnah.

### FQ-031 Munajat-e-Maqbool daily manzil
- Manual user choice of manzil 1–7 and edition; app contains no published book text and assumes neither a canonical weekday mapping nor a binding obligation.
- **Status: `USER-PERSONAL-GOAL`; bibliographic/edition mapping `UNVERIFIED`.**

## Questions that remain open (none are resolved by the owner decisions above)

FQ-003 whether Witr Qaza is obligatory for this user; FQ-005 which Asr/twilight convention applies; FQ-004 whether any order applies; FQ-006 preferred time; FQ-007 Tahajjud; FQ-008/009 Ishraq and Duha; FQ-010 Awwabeen; FQ-013 voluntary Qaza; FQ-014 historical Qaza estimation; FQ-015/016/018/019/020/022 fasting rules; FQ-021 Hijri method.

## Changelog

- 0.1.3: source-link selected daily Masnoon Duas/Sunnah (FQ-028–031), distinctly warn about disputed 7x Hasbiyallahu report; no exhaustive/authenticated-canon claim.
- 0.1.2: add owner-supplied Salawat/Dhikr and Dala'il manzil tracking questions (FQ-024–027); no claims authenticated.
- 0.1.1: record owner engineering decisions (Witr configurable, Asr configurable, no ordering enforcement, Hijri configurable without algorithm); no rule status changed.
- 0.1.0: initial draft, all rules `UNVERIFIED`.
