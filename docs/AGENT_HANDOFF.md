# AI Agent Handoff — Ibadah Companion

**Date 2026-10-18.** GitHub is the permanent shared memory across ChatGPT and multiple Claude Chat accounts. NEVER use this document alone to infer current PR/branch status; verify HEAD, open PRs and Actions results first.

## Identity

Repository: https://github.com/muhammadtwaha79/Personal-Islamic-App (public). Native Kotlin Android, Compose/Material3/Hilt/Room, application ID `com.muhammadtwaha.ibadahcompanion`, minSdk26, offline-first with **no INTERNET permission**. Personal prayer/dhikr/Quran records live only on the user's phone, never in GitHub.

## Integrated baseline

PRs #1–6 were merged. Five-Fard tracking, Jamaat/Takbeer challenge, Sunnah/Nafl, Quran daily reading/Ramadan planner, 1200 Salawat, 1200 Istighfar, 1000 Bismillah, after-Fard 20 Salawat and Dala'il manzil currently exist in `main`, Room schema v4. [Salawat successful CI run 39108600987](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39108600987) built debug APK.

## Active task and branch ownership

- Current working branch: `feature/daily-masnoon-adhkar-sunnah-munajat` (ChatGPT session), PR #7: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/7.
- Sourced starter catalog `domain/masnoon/MasnoonCatalog.kt`: 28 practice entries and 10 Sunnah checklist items, each with source/grade metadata. Never claim “all Sunnah” or an absolutely certified Arabic corpus. Morning/evening scopes separated and the 7x Hasbiyallahu Abu Dawud 5081 report is **explicitly DISPUTED** (graded Mawdu' by al-Albani on cited listing), excluded from standard completion.
- User-facing `MasnoonScreen.kt` opens from the Dhikr tab (“Masnoon & Munajat”) with Morning, Evening, Daily Duas, After Salah, Sunnah Habits, Munajat.
- `MasnoonRepository` appends recitation count events to existing DhikrDao, stores optional Sunnah checkboxes and `Munajat-e-Maqbool` chosen manzil 1–7 separately. Database schema v5 with **additive 4→5 migration**, no destructive/reset paths added. Dala'il manzil remains separate.
- Owner authorizes self-managed GitHub PR merges AFTER successful CI, never force-push; only one writing agent owns active branch at a time.

## Exact next steps

1. Inspect PR #7 live HEAD and its **matching** Actions workflow. This document was written while docs commits were still being added; early run 39110939138 was cancelled by concurrency. **Do not call the feature CI-verified until the CURRENT SHA succeeds** with Kotlin compile/JUnit/lint/APK upload.
2. Investigate any compiler/Room error using the GitHub job logs if available; fix without dropping tests or resorting to destructive Room migration.
3. Once final exact SHA passes and uploaded debug APK is verified, mark PR #7 ready and **merge into main** under owner authorization; verify merge result.
4. If handoff still calls PR #7 open after merge, use a small docs-only post-merge PR and merge after green CI.
5. User will then install APK (they have NOT yet installed any version) and report app launch, Save/Undo, morning-evening independence and persistence after restart.
6. Continue reliability: Android device/emulator migrations v1→2→3→4→5, safe export/restore, tests, accessibility and Arabic text scholarly review. Qaza recovery ledger and fasting are still pending original goals.

## Religious sourcing

The library is a **curated verified-reference starter**, not comprehensive. [Research source register](research/masnoon-source-register.md) contains book/hadith links and reported grades. Sayyidul Istighfar Bukhari 6306, 3 Quls Abu Dawud 5082, Bismillah 5088, Tasbih Muslim 2692. The controversial Hasbiyallahu 7x report is NOT labelled authenticated Sunnah; read [ADR-0019](decisions/0019-source-linked-daily-masnoon-adhkar.md). Long Arabic matn/variants need independent expert review.

## Safety

No personal worship databases/keystores/tokens/logs on public GitHub. No INTERNET permission/analytics. No encrypted export/restore or phone migration testing exists; **do not rely on this APK as the sole historical ledger** until proven safe. No prescribed reward guarantees or religious ranking from checklist metrics. Keep source/corrections/provenance accurate. See [Roadmap](ROADMAP.md) and [ENGINEERING_STATUS](ENGINEERING_STATUS.md).
