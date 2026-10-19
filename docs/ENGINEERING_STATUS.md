# Engineering Status — Ibadah Companion

**Snapshot: 2026-10-19.** Live GitHub evidence overrides this document. Read [CLAUDE.md](../CLAUDE.md) and [AGENT_HANDOFF.md](AGENT_HANDOFF.md) before writing.

## Merged baseline

- PRs #1–8 previously merged. Native offline Android Kotlin/Compose/Material3/Hilt/Room v5 with Salah, Sunnah/Nafl, Quran/Ramadan, Salawat/Dhikr, Takbeer 40-day challenge, Dala'il/Munajat trackers.
- Old UI was **installed on the owner's real Android phone** in October 2026; owner explicitly reported poor visual design. No detailed persistence/restart test yet.
- Existing verified compiled APK: [run 39111903815](https://github.com/muhammadtwaha79/Personal-Islamic-App/actions/runs/39111903815).

## Active work — Premium UI redesign

- Development branch: `design/elite-home-worship-ui`. Find its active PR on GitHub and inspect the exact latest head.
- [IMPLEMENTED ON BRANCH] Home-first navigation with actual prayer progress hero, 6 original Compose Canvas illustrated module tiles, light/dark emerald/ivory design theme, compact 4-tab bottom bar.
- [IMPLEMENTED ON BRANCH] Salah prayer cards with separate dedicated two-step dialog to select Fard completion/Jamaat/Takbeer/missed/unknown and explicitly confirm; Friday Dhuhr/Jumu'ah requires user selection. Reuses existing transaction and Salah revision history.
- [IMPLEMENTED ON BRANCH] Sunnah/Nafl card UI, Quran daily goal/Surah/Ramadan layouts, 40-day challenge dashboard, Salawat/Dhikr counters, and new combined Dala'il + Munajat Manzils screen with selection and undo.
- [REMOVED FROM ACTIVE APP] Masnoon Duas module/UI and previous catalog; owner requested its removal. Historical religious/source research docs persist, but Masnoon catalog is no longer a user-facing route. **Legacy database tables and records are deliberately preserved** for non-destructive upgrade; Room version stays **v5**.
- [PENDING VERIFICATION] Changes have been committed on the feature branch but require exact-head GitHub Actions compile, JUnit tests, lint, APK upload and subsequent real-phone UI smoke check. **Do not claim green CI before checking it.**

## Critical constraints

- DEBUG-SIGNING: a fresh ephemeral GitHub Actions runner may sign new APK with a different debug key. Upgrading an installed APK can fail with signature conflict. **No stable managed release-signing key / private keystore has been provisioned.** Do not tell the owner to uninstall or clear an app containing historical personal records without safe backup.
- APP DATA: offline local non-encrypted SQLite; Android backup disabled and encrypted export/restore not implemented.
- RELIGION: preserve source/fiqh caveats and no reward guarantee. Previous Masnoon source materials are historical docs, not an active educational catalog.
- OUTSTANDING: Qaza ledger, fasting, migration instrumentation, accessibility, secure backup/export and stable signed release.

## Next exact action

1. Verify latest branch head has passing GitHub Actions build, test and lint, and an uploaded debug APK artifact. Fix build issues, update handoff, then merge PR under owner's existing blanket authorization.
2. User visually tests the redesigned app on their Android phone (Home six tiles, Salah two-step confirm, Nafl, Quran, Dhikr, Manzils, 40-Day) and reports actual UI usability.
3. Address stable signing and safe export/restore before installing over a data-bearing old APK.
4. Continue Qaza/fasting roadmap after UI acceptance.

See [ADR-0020](decisions/0020-home-first-visual-redesign-and-masnoon-removal.md).
