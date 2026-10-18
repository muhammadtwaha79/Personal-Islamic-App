# Engineering Status — Ibadah Companion

**Snapshot: 2026-10-18.** Verify current GitHub HEAD, PRs and CI instead of trusting this file uncritically. Rules: [CLAUDE.md](../CLAUDE.md). Handoff: [AGENT_HANDOFF.md](AGENT_HANDOFF.md).

## Verified merged milestones

- [VERIFIED] Phase 0 PR #1 merged.
- [VERIFIED] Phase 1 PR #2 merged to `main` as `170135078fb6dcee6433ef2b51d22cc6d8f3df37`.
- [VERIFIED] Phase 2 feature PR #3 merged to `main` as **`ca2f596dbca76edbf0aa1c603e60b674922947a9`**.
- [VERIFIED] CI on final Phase 2 PR head `ca2534d46990039a0ba4fb3b71ffb8098a9bb6b9`: https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39103550819 — Android wrapper validation, compilation, unit tests, lint, APK assembly, reports and debug APK artifact upload succeeded.
- Debug APK artifact `ibadah-companion-debug-apk`, ID `12127319015`, uploaded to that run. Artifact archive byte count 12,061,047; this is **not** the raw APK payload's SHA-256.

## Features implemented in merged Phase 2 MVP

- Five daily Fard self-reported Salah, Jamā‘ah vs individual, opening Takbeer yes/no/unknown, joined-late, explicit missed/unknown, optional Friday Dhuhr/Jumu'ah label. Current record and immutable Salah revisions saved with Room.
- Fixed personal 40-day challenge starting **2026-10-20**, 200 opportunities, confirmed opportunities and daily consecutive-streak arithmetic.
- Sunnah/Nafl personal log: Tahajjud, Ishraq, Chasht, Awwabeen, four pre-Asr, four pre-Isha and other common optional prayers, adjustable rakahs.
- Quran half-para daily from 2026-10-20, independent Yaseen/Waqiah/Mulk check-ins.
- Manually configured Ramadan dates, target 5 **confirmed** full Quran khatams and 150 juz volume (kept separate), with tracked self-confirmations and undo events.
- Local Room v3 schema with explicit 1→2→3 migration chain; Kotlin domain tests in CI.
- Four Compose tabs, no INTERNET permission, no required login, no ads or analytics.

## Important limitations

- [NOT VERIFIED] APK installation or real-device UI; no Android emulator end-to-end tests, or populated Room migration tests. These should be the first hardening actions.
- [NOT IMPLEMENTED] Qaza ledger, fasting, Witr tracking, verified fiqh profile/onboarding, prayer-time calculation, repeatable 40-day challenges, Adhkar/Duas, reminders, encrypted backup/app lock and production release.
- [SECURITY] Offline SQLite is not claimed encrypted; Android auto-backup disabled, so uninstall/device loss can irreversibly erase worship records until verified backup exists.
- [RELIGIOUS] Takbeer-e-Ula, Jumu'ah, Sunnah categories, Hijri timing and reward-related rules require qualified review and remain UNVERIFIED. App does not claim any guarantee of divine acceptance.

## Next task

The owner authorized agents to handle verified PR merges. Following this documentation PR, prioritize **device/emulator launch and Room migration testing, plus secure local export** before the user relies solely on the app. Next feature request is Adhkar/Duas after existing Salah/Nafl/Quran tracking; the existing Qaza and fasting roadmap must also continue. Full product roadmap: [ROADMAP](ROADMAP.md), requirements: [Owner brief](requirements/2026-10-worship-and-quran-owner-brief.md).

GitHub is the permanent cross-account memory; consult [AGENT_HANDOFF](AGENT_HANDOFF.md), and do not modify `main` without review or force-push.
