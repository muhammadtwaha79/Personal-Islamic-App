# Agent Handoff — Ibadah Companion

**Snapshot 2026-10-18. GitHub is the authoritative memory shared among Claude Chat accounts and ChatGPT.** Before any action: verify live `main` head, open PRs, latest commits and GitHub Actions. Read [CLAUDE.md](../CLAUDE.md), [ENGINEERING_STATUS.md](ENGINEERING_STATUS.md), [SESSION_LOG.md](SESSION_LOG.md).

## Identity and integrated state

Repo: https://github.com/muhammadtwaha79/Personal-Islamic-App (public). Android app ID `com.muhammadtwaha.ibadahcompanion`; Kotlin/Compose/Material3/Hilt/Room v5, AGP9.4/Gradle9.6, minSdk26, fully offline, no INTERNET permission.

- PR #1–6: merged.
- **PR #7 merged** `baab2cf3320c3a9ab30982b88abc8d744c0199c2`, https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/7 .
- [Verified source-head build](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39111339151): final PR #7 head `5960a199174914099824e869780f454430e5e9e3`; wrapper, compile, JUnit, lint and APK artifact upload all successful. Artifact `ibadah-companion-debug-apk`, ID `12130677678`. **No device installation has been verified by the owner yet.**
- No active source feature branch is held after PR #7 merge. This `docs/postmerge-masnoon-handoff` is a documentation synchronization branch; check [PR #8](https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/8) actual status.

## Implemented core (not necessarily production-verified)

Five Fard Salah with Jamaat/Takbeer, 40-day tracking from 20 Oct 2026, Sunnah/Nafl, Quran/Ramadan five-khatam planner, daily Salawat 1200, Istighfar 1200, Bismillah 1000, 20 personal after-Fard Salawat with owner-supplied Arabic, Dala'il daily manzil. New Masnoon source-linked starter catalog **28** dua/adhkar items and **10** Sunnah habit check-ins, Morning/Evening separate counters, 3 Qul recitations three times each, Sayyidul Istighfar, daily life and after-Fard adhkar, and separate user-selectable Munajat-e-Maqbool manzil 1–7. Room schema v5 uses additive 4→5 migration and previously existing append-only signed recitation event journal.

## Religious provenance and scope guardrails

- Source pages and reported grades are in [source register](research/masnoon-source-register.md). **7× Hasbiyallahu** Abu Dawud 5081 was graded `Mawdu'` (fabricated) by al-Albani on the cited page; it is optional with warning and EXCLUDED from ordinary trusted-goal completion. No reward guarantee.
- 28+10 catalog is a **source-linked starter subset**, NOT every authentic prayer or daily Sunnah; some longer Arabic spelling/variants still require specialist proofreading.
- Existing 20 user-supplied Sufi/other named Salawat attributions are UNVERIFIED; do not silently certify texts or rank religious merit. No published Munajat or Dala'il book text embedded.
- All goals are chosen by the owner and self-reported; missing optional events never create an obligatory debt.

## Next exact action

1. Verify latest merged PRs and status of postmerge docs PR #8. If open, inspect CI for its exact head and merge if green.
2. User has not yet installed Android APK. Direct them to [run 39111339151](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39111339151) → Artifacts → `ibadah-companion-debug-apk` → extract ZIP → install `app-debug.apk`. Test app launch, navigation Dhikr → Masnoon & Munajat, morning/evening independence, correction and restart persistence. Do not claim these device tests succeeded before user feedback.
3. Future engineering work: Android/emulator v1–v5 populated Room migration and UI tests; safe encrypted backup/export and data security; Qaza ledger and fasting (original user goals), then accessible learning modes and Arabic/fiqh editorial review.
4. Make all changes in new reviewable branches; **owner previously authorized agent-managed merges only after green CI**. Never force push or overwrite someone else's branch.

## Data protection and limitations

Repository is public. No personal prayer/dhikr data, secrets, keystores or device backups in GitHub. Android auto-backup disabled; no backup/restore; uninstall or clear data could permanently erase personal records. Existing CI is **not** evidence of device stability. Respect user choice and do not manufacture religion-related assessments.

Useful docs: [Engineering Status](ENGINEERING_STATUS.md) · [Roadmap](ROADMAP.md) · [Research](research/masnoon-source-register.md) · [ADR-0019](decisions/0019-source-linked-daily-masnoon-adhkar.md).
