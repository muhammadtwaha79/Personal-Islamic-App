# Engineering Status — Ibadah Companion

**Snapshot 2026-10-18.** Live GitHub state and CI override any stale snapshot. [CLAUDE.md](../CLAUDE.md) · [AGENT_HANDOFF.md](AGENT_HANDOFF.md)

## Merged stable foundation

- Phase 0 PR #1, Phase 1 PR #2 (merge `1701350`), Phase 2 PR #3 (merge `ca2f596`), and post-merge documentation PR #4 (merge `b4d7879c44ad573f3ee4b7fb7e9d11fe0b2d88e3`) are confirmed merged.
- Native Kotlin/Compose/Room foundation with five-prayer Salah, Jamaat/Takbeer, 40-day challenge, Sunnah/Nafl and Quran/Ramadan tilawat tracking is on `main`.
- Prior verified successful worship tracker CI: https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39103550819

## Current feature — Salawat and daily Dhikr

- [MERGED] Salawat / daily Dhikr PR #5: https://github.com/muhammadtwaha79/Personal-Islamic-App/pull/5 — merge commit **`5edc84e4d77612b32535e126cfd026b4d312d45c`**. The previous development branch `feature/salawat-adhkar-counting` is now historical.
- [IMPLEMENTED] 1,200x independent short Salawat, 1,200x Istighfar, 1,000x Bismillah daily goals, signed adjustments with transactional nonnegative count checks.
- [IMPLEMENTED] 20 owner-supplied Arabic Salawat each with a **personal goal 10x after each of five Fard prayers**; 100 separate after-Fard goals. Full Arabic text shown in an RTL dialog. Source/attribution/merit claims are UNVERIFIED.
- [IMPLEMENTED] Dala'il al-Khayrat daily manzil 1–7 manual selection, edition label, completion checkbox. The copyrighted book itself is not included; no edition day mapping is assumed.
- [IMPLEMENTED] Room schema v4 (new `recitation_event` and `dalail_daily` tables), explicit additive 3→4 migration, date/practice/prayer index for efficient queries, event journal and basic JVM rules tests.
- [VERIFIED] Final Salawat PR #5 head **`5e29b81be44c57af8e5542e0440532a290627d41`** passed full Android CI **39108600987**: wrapper, compilation, unit tests, lint, APK assembly and artifact upload all succeeded. [View verified run](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39108600987). APK artifact `ibadah-companion-debug-apk`, ID **12128819307** (GitHub artifact ZIP ≈ 12.1 MB; not the raw APK SHA-256). GitHub confirmed PR #5 merge.

## Quality and religious limitations

- **No real device or populated Room migration 1→2→3→4 test has been executed.** CI validation is not equivalent to on-device data safety.
- A local SQLite database is **not encrypted** and backup/restore are not yet implemented; clearing app storage/uninstalling may permanently erase user records. Do not use the app as the only long-term record until this is addressed.
- Quran/Qaza/fasting source-sensitive religious questions, Salawat attributions and Dala'il editions require qualified textual/fiqh review. App targets are OWNER-PERSONAL and do not claim a universal reward or binding obligation.
- Qaza ledger, fasting, advanced Adhkar/Duas source library, optional reminders, app lock and encrypted export remain incomplete.

## Exact next task

1. Ask the owner to download the new APK from [run 39108600987](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39108600987) and verify installation, app restart persistence, +1/+10 corrections, Arabic view, after-Fard counters and Dala'il manzil on device.
2. Add an Android instrumented **populated Room v3→v4 migration test** and restore/export path before depending on local SQLite as the only long-term record.
3. Continue previously approved Qaza obligations ledger and fasting plus the newly scoped authenticated morning/evening Adhkar and Duas library. Preserve Arabic text/source review status.
