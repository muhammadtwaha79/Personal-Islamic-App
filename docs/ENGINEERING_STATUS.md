# Engineering Status — Ibadah Companion

**Snapshot 2026-10-18.** Live GitHub state and CI override any stale snapshot. [CLAUDE.md](../CLAUDE.md) · [AGENT_HANDOFF.md](AGENT_HANDOFF.md)

## Merged stable foundation

- Phase 0 PR #1, Phase 1 PR #2 (merge `1701350`), Phase 2 PR #3 (merge `ca2f596`), and post-merge documentation PR #4 (merge `b4d7879c44ad573f3ee4b7fb7e9d11fe0b2d88e3`) are confirmed merged.
- Native Kotlin/Compose/Room foundation with five-prayer Salah, Jamaat/Takbeer, 40-day challenge, Sunnah/Nafl and Quran/Ramadan tilawat tracking is on `main`.
- Prior verified successful worship tracker CI: https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39103550819

## Current feature — Salawat and daily Dhikr

- Branch: `feature/salawat-adhkar-counting`.
- PR #5: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/5
- [IMPLEMENTED] 1,200x independent short Salawat, 1,200x Istighfar, 1,000x Bismillah daily goals, signed adjustments with transactional nonnegative count checks.
- [IMPLEMENTED] 20 owner-supplied Arabic Salawat each with a **personal goal 10x after each of five Fard prayers**; 100 separate after-Fard goals. Full Arabic text shown in an RTL dialog. Source/attribution/merit claims are UNVERIFIED.
- [IMPLEMENTED] Dala'il al-Khayrat daily manzil 1–7 manual selection, edition label, completion checkbox. The copyrighted book itself is not included; no edition day mapping is assumed.
- [IMPLEMENTED] Room schema v4 (new `recitation_event` and `dalail_daily` tables), explicit additive 3→4 migration, date/practice/prayer index for efficient queries, event journal and basic JVM rules tests.
- [VERIFIED ON EARLIER FEATURE HEAD] GitHub Actions run **39108218410** concluded **success** on implementation SHA `61ea289b8983bf6c2b7f6a31cf303d7707884bf8`: wrapper validation, Kotlin compilation, JVM tests, lint, APK creation and artifact upload. Debug APK artifact ID **12129013814**.
- [PENDING VERIFICATION] Index and documentation commits after `61ea289` need final-head CI before PR #5 can merge. Do not treat the earlier run as confirmation of the latest revision.

## Quality and religious limitations

- **No real device or populated Room migration 1→2→3→4 test has been executed.** CI validation is not equivalent to on-device data safety.
- A local SQLite database is **not encrypted** and backup/restore are not yet implemented; clearing app storage/uninstalling may permanently erase user records. Do not use the app as the only long-term record until this is addressed.
- Quran/Qaza/fasting source-sensitive religious questions, Salawat attributions and Dala'il editions require qualified textual/fiqh review. App targets are OWNER-PERSONAL and do not claim a universal reward or binding obligation.
- Qaza ledger, fasting, advanced Adhkar/Duas source library, optional reminders, app lock and encrypted export remain incomplete.

## Exact next task

1. Inspect PR #5's **current** head SHA; wait for full Android CI success plus debug APK artifact upload for that SHA. Fix failures and reverify if necessary.
2. Merge PR #5 into main using the user's standing authorization once verified; verify GitHub merge result. Record final merge commit in a new small docs-only update if needed.
3. Ask owner to test APK installation and count persistence on a real phone; add Android migration/device instrumentation before treating the tracker as production-reliable.
4. Next milestones: secure backup/restore, append-only Qaza accounting, fasting, optional sourced Adhkar/Duas, and Arabic textual/source review.
