# Premium UI modernization — 2026-10-19

**Task:** `design/elite-home-worship-ui` (active; verify GitHub before relying on status). [ADR-0020](../decisions/0020-home-first-visual-redesign-and-masnoon-removal.md).

## User's requested visible changes

- Illustrated home module cards as the primary entry point; do not scatter all controls on the opening screen.
- Clean visually unified navigation, strong typography and calm emerald/ivory backgrounds; dark mode.
- Fard Salah cards show a status at a glance; distinct detail dialog with clear, deliberate confirmation instead of a dozen flat buttons.
- Sunnah/Nafl completion and rakah selection, Quran reading progress and Surah confirmations, Dhikr counters, 40-day challenge and Dala'il/Munajat reading cards should share the same visual system.
- Remove **Masnoon Duas** from the active product. Maintain historical source material in docs and retain database compatibility, but do not show source-linked Masnoon lists or counters in current UI.

## Engineering QA plan

1. Kotlin and Compose compile, JUnit and Android lint pass on the exact PR source SHA.
2. Check all six home routes work: Salah, Quran, Nafl, Dhikr, Challenge, Manzils; ensure no Masnoon route is reachable.
3. Confirm Salah Fajr–Isha dialog saves correct state; Friday choose Jumu'ah or Dhuhr; incomplete/future data never treated as fulfilled.
4. Verify no Room entities removed, database remains version 5 and *Munajat* and *Dala'il* historic entries still display.
5. Validate real device screenshot/UI legibility and scroll in portrait. First user test report about older APK: successfully installed, found UI poor.
6. Check debug signer continuity before installing over an old build. Until a durable signing plan and safe backup exist, do not advise the owner to uninstall any data-bearing app.

## Priorities after redesign

Safe user-controlled export/restore and stable signing, migration tests and UI acceptance; then original Qaza and fasting functionality. User's requested removal of Masnoon Duas supersedes the earlier requirement to build that module.
