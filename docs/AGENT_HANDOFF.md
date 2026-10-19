# Agent Handoff — Ibadah Companion

**2026-10-19.** Shared institutional memory across Claude and ChatGPT accounts is GitHub, not chat. Always verify live repository `main` and open PRs before operating. Read [CLAUDE.md](../CLAUDE.md), [ENGINEERING_STATUS.md](ENGINEERING_STATUS.md), [SESSION_LOG.md](SESSION_LOG.md).

## Repo identity and previous milestones

https://github.com/muhammadtwaha79/Personal-Islamic-App — public repository, Android minSdk26, Kotlin/Compose/Material3/Hilt/Room v5, no INTERNET permission and no analytics. PR #1–8 were merged. The owner **physically installed a previous debug APK** and disliked the UI; no documented device/data persistence test yet.

## Current active branch

- **`design/elite-home-worship-ui`** is the UI overhaul branch. User personally requested: polished premium aesthetic; home-page illustration/picture-like module cards; much better Salah/other confirmation buttons; **remove Masnoon Duas**.
- Changes: `HomeScreen.kt` now functional home dashboard with live same-day prayer progress and six built-in vector picture cards; `AppNavHost.kt` start route is `home`, compact four-tab bar; separate routes `salah`, `quran`, `nafl`, `dhikr`, `challenge`, `manzils`.
- `EliteUi.kt` has reusable card/pill/choice/Canvas art; `Theme.kt` has cohesive emerald/ivory light/dark palettes; `SalahScreen.kt` has choose-then-confirm prayer dialog; Nafl/Quran/Dhikr/Challenge have revised cards and controls.
- `ManzilsRepository.kt` and `ManzilsScreen.kt` preserve Munajat and Dala'il without the Masnoon catalog. Masnoon screen/catalog/repository and catalog test were removed from source. **Keep the existing Room database entities, MasnoonDao and v5 legacy tables**; destroying them would risk personal record loss. Do not assume anyone authorized data deletion.
- Design decision: [ADR-0020](decisions/0020-home-first-visual-redesign-and-masnoon-removal.md). Product acceptance checklist: [2026-10 UI plan](phases/2026-10-elite-home-redesign-plan.md).

## Exact next actions

1. Open/check PR for `design/elite-home-worship-ui`; verify current head GitHub Actions compile/test/lint and debug APK upload. If fails, fetch job logs, fix, rerun. Do **not** declare complete on an old green PR.
2. Merge reviewed green PR into `main` (standing user authorization to manage PR merges) only after final exact SHA passes, then fix stale postmerge GitHub docs if needed.
3. Owner tests new Home cards and Salah/Nafl confirmation flows on physical phone. Ask for screenshots only of UI (not private worship history).
4. IMPORTANT: GitHub-runner debug signing key may rotate. If the already installed old APK has data, **do not suggest uninstall / clear data** to install newer differently signed APK. Stable secret-backed app signing or verified export/restore is a future prerequisite for safe upgrades. No secrets or signing keys should be committed to public GitHub.
5. After UX validation, implement data-safe export/restore, populated Room migration device tests, Qaza obligations ledger, fasting and accessibility improvements.

## Religious/product constraints

No fictional hadith rewards, unverified source claims or prayers automatically marked performed. The previous source-linked Masnoon Duas feature has been removed **by owner decision**; do not restore it without explicit request. Munajat-e-Maqbool and Dala'il al-Khayrat manzil checkboxes remain. User plans begin 20 October 2026. All personal religious history stays on phone, not GitHub.
