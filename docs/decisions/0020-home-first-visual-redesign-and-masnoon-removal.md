# ADR-0020 — Home-first illustrated redesign and user-requested removal of Masnoon Duas

**Status:** Accepted product UX direction (2026-10-19); Android CI/device verification still required.

## Owner feedback

The owner installed the APK on a real phone and described its existing UI as severely unattractive. Requested:
1. An **elite visual redesign**, beyond color replacement;
2. Home page with illustration/picture-like cards opening every active module;
3. Elegant Salah and other worship confirmation buttons;
4. **Remove the Masnoon Duas feature**;
5. Preserve continuing Salah/Nawafil/Quran/Salawat/Munajat and Dala'il manzil tracking.

## Decision

- Home is now the start destination, with a graceful greeting, real same-day Salah progress hero and six illustrated navigation cards: Daily Salah, Quran, Sunnah & Nafl, Salawat & Dhikr, 40-Day Journey, and Manzils. Illustrations are locally drawn vector Canvas art; no network/media dependencies.
- Bottom navigation reduced to four frequently-used sections: Home, Salah, Quran, Dhikr. Less frequent modules are available through home tiles.
- Material 3 premium ivory/emerald/gold visual system, typographic hierarchy, rounded content cards and touch targets. Preserve dark mode and offline support.
- Salah becomes a **two-step confirmation**: select prayer card, choose exactly one self-reported state, confirm. Friday Jumu'ah vs Dhuhr is explicitly selected rather than automatically assumed. Future dates cannot be marked completed. Corrections use existing transactional Room repository; no destructive database changes.
- Nawafil, Quran, challenge, Salawat/Dhikr and manzil screens get coordinated layouts and clear confirmation/progress controls.
- **Masnoon Duas catalog and Masnoon screen are discontinued** by user direction; active navigation no longer links to them. Keep the previous Room v5 schema and stored tables/records intact rather than destroying user data. The separate Munajat-e-Maqbool check-in is retained in a new combined Manzils screen alongside Dala'il al-Khayrat.
- Religious questions/attribution/source research in prior documents are preserved as historical institutional memory; they are no longer active user-facing Masnoon features.

## Acceptance gates

- Current branch compiles with Gradle Wrapper, JVM tests and Android lint passing; GitHub Actions produces debug APK.
- Main cannot be merged until the **exact PR head** is green and artifact uploaded.
- User phone testing still required after install: Home tile navigation; Salah Confirm/Undo; Quran, Nafl, Dhikr and Manzils flows; persistence after restarting; dark mode and narrow screen.
- Preserve Room database schema v5 so existing user data is not deleted or erased by a UI update. Migration path unchanged.
- The GitHub-generated debug certificate can vary by runner. A stable secure release signing key is still needed for reliable upgrades. **Do not recommend uninstall/clear-data without backup confirmation.**

## Outstanding

- No real device screenshots or Compose screenshot regression suite yet. Color/contrast and touch targets should be validated on the owner's actual Android phone.
- Encrypted backup/export and durable release signing remain high priority before long-term daily record keeping.
- Qaza ledger and fasting remain on product roadmap and are not asserted complete.
