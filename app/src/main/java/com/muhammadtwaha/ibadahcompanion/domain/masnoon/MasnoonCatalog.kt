package com.muhammadtwaha.ibadahcompanion.domain.masnoon

/**
 * A carefully SOURCE-LINKED STARTER CATALOG, NOT an exhaustive inventory of Sunnah.
 *
 * Sources checked 2026-10-18 on Sunnah.com; editorial Arabic orthography is still
 * provisional and a scholar should check longer wordings and every grading.
 * No entry may assert guaranteed protection, cure, reward or spiritual status.
 */
enum class Window { MORNING, EVENING, OCCASION, AFTER_SALAH }
enum class Evidence { SAHIH, SAHIH_GRADED, HASAN, HASAN_CHAIN, DISPUTED }

data class Practice(
    val id: String,
    val title: String,
    val arabic: String,
    val target: Int,
    val windows: Set<Window>,
    val reference: String,
    val sourceUrl: String,
    val evidence: Evidence,
    val note: String,
) {
    val disputed: Boolean get() = evidence == Evidence.DISPUTED
}
data class SunnahHabit(
    val id: String, val title: String, val reference: String,
    val sourceUrl: String, val note: String,
)

object MasnoonCatalog {
    val practices: List<Practice> = listOf(
        Practice("SAYYID_ISTIGHFAR", "Sayyidul Istighfar", "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ وَأَبُوءُ لَكَ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ", 1, setOf(Window.MORNING, Window.EVENING), "Sahih al-Bukhari 6306", "https://sunnah.com/bukhari:6306", Evidence.SAHIH, "Authentic daytime and nighttime wording; morning/evening repetition is a tracker convention, not an additional hadith claim."),
        Practice("IKHLAS", "Surah al-Ikhlas (112)", "سُورَةُ الْإِخْلَاصِ (١١٢) — اِقْرَأِ السُّورَةَ مِنَ الْمُصْحَفِ", 3, setOf(Window.MORNING, Window.EVENING), "Sunan Abi Dawud 5082", "https://sunnah.com/abudawud:5082", Evidence.HASAN, "Read the complete surah from your Mushaf; title is not the full surah text."),
        Practice("FALAQ", "Surah al-Falaq (113)", "سُورَةُ الْفَلَقِ (١١٣) — اِقْرَأِ السُّورَةَ مِنَ الْمُصْحَفِ", 3, setOf(Window.MORNING, Window.EVENING), "Sunan Abi Dawud 5082", "https://sunnah.com/abudawud:5082", Evidence.HASAN, "Read the complete surah from your Mushaf; title is not the full surah text."),
        Practice("NAS", "Surah an-Nas (114)", "سُورَةُ النَّاسِ (١١٤) — اِقْرَأِ السُّورَةَ مِنَ الْمُصْحَفِ", 3, setOf(Window.MORNING, Window.EVENING), "Sunan Abi Dawud 5082", "https://sunnah.com/abudawud:5082", Evidence.HASAN, "Read the complete surah from your Mushaf; title is not the full surah text."),
        Practice("BISMILLAH_PROTECTION", "Bismillahilladhi la yadurru", "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الْأَرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ", 3, setOf(Window.MORNING, Window.EVENING), "Sunan Abi Dawud 5088", "https://sunnah.com/abudawud:5088", Evidence.SAHIH_GRADED, "Three repetitions as reported; no promise that reciting removes all risk."),
        Practice("SUBHANALLAH_100", "Subhanallahi wa bihamdihi", "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ", 100, setOf(Window.MORNING, Window.EVENING), "Sahih Muslim 2692", "https://sunnah.com/muslim:2692", Evidence.SAHIH, "100 in morning and 100 in evening."),
        Practice("ASBAHNA", "Allahumma bika asbahna", "اللَّهُمَّ بِكَ أَصْبَحْنَا وَبِكَ أَمْسَيْنَا وَبِكَ نَحْيَا وَبِكَ نَمُوتُ وَإِلَيْكَ النُّشُورُ", 1, setOf(Window.MORNING), "Sunan Abi Dawud 5068", "https://sunnah.com/abudawud:5068", Evidence.SAHIH_GRADED, "Morning wording."),
        Practice("AMSAYNA", "Allahumma bika amsayna", "اللَّهُمَّ بِكَ أَمْسَيْنَا وَبِكَ نَحْيَا وَبِكَ نَمُوتُ وَإِلَيْكَ النُّشُورُ", 1, setOf(Window.EVENING), "Sunan Abi Dawud 5068", "https://sunnah.com/abudawud:5068", Evidence.SAHIH_GRADED, "Evening wording; variants exist."),
        Practice("AFW_AFIAH", "Allahumma inni as'alukal afwa wal afiyah", "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَافِيَةَ فِي الدُّنْيَا وَالْآخِرَةِ اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي دِينِي وَدُنْيَايَ وَأَهْلِي وَمَالِي اللَّهُمَّ اسْتُرْ عَوْرَاتِي وَآمِنْ رَوْعَاتِي اللَّهُمَّ احْفَظْنِي مِنْ بَيْنِ يَدَيَّ وَمِنْ خَلْفِي وَعَنْ يَمِينِي وَعَنْ شِمَالِي وَمِنْ فَوْقِي وَأَعُوذُ بِعَظَمَتِكَ أَنْ أُغْتَالَ مِنْ تَحْتِي", 1, setOf(Window.MORNING, Window.EVENING), "Sunan Abi Dawud 5074", "https://sunnah.com/abudawud:5074", Evidence.SAHIH_GRADED, "Full wording, minor manuscript and recitation variants exist; editorial review pending."),
        Practice("AFINI_BADANI", "Allahumma afini fi badani", "اللَّهُمَّ عَافِنِي فِي بَدَنِي اللَّهُمَّ عَافِنِي فِي سَمْعِي اللَّهُمَّ عَافِنِي فِي بَصَرِي لَا إِلَهَ إِلَّا أَنْتَ", 3, setOf(Window.MORNING, Window.EVENING), "Sunan Abi Dawud 5090", "https://sunnah.com/abudawud:5090", Evidence.HASAN_CHAIN, "Three repetitions of this portion; remainder of reported passage may be tracked after textual review."),
        Practice("HASBI_SEVEN", "Hasbiyallahu (optional; disputed report)", "حَسْبِيَ اللَّهُ لَا إِلَهَ إِلَّا هُوَ عَلَيْهِ تَوَكَّلْتُ وَهُوَ رَبُّ الْعَرْشِ الْعَظِيمِ", 7, setOf(Window.MORNING, Window.EVENING), "Sunan Abi Dawud 5081; graded mawdu' by al-Albani", "https://sunnah.com/abudawud:5081", Evidence.DISPUTED, "OPTIONAL: the 7x attribution/reward report is graded mawdu' on the cited page; not an established Prophetic morning/evening prescription."),
        Practice("WAKE_DUA", "Upon waking", "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ", 1, setOf(Window.OCCASION), "Sahih al-Bukhari 6324", "https://sunnah.com/bukhari:6324", Evidence.SAHIH, "After waking from sleep."),
        Practice("SLEEP_DUA", "Before sleeping", "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا", 1, setOf(Window.OCCASION), "Sahih al-Bukhari 6324", "https://sunnah.com/bukhari:6324", Evidence.SAHIH, "Before sleep."),
        Practice("TOILET_ENTRY", "Before entering toilet", "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْخُبُثِ وَالْخَبَائِثِ", 1, setOf(Window.OCCASION), "Sahih al-Bukhari 142", "https://sunnah.com/bukhari:142", Evidence.SAHIH, "Supplication before entry."),
        Practice("TOILET_EXIT", "After leaving toilet", "غُفْرَانَكَ", 1, setOf(Window.OCCASION), "Sunan Abi Dawud 30", "https://sunnah.com/abudawud:30", Evidence.SAHIH_GRADED, "Supplication after leaving."),
        Practice("LEAVE_HOME", "When leaving home", "بِسْمِ اللَّهِ تَوَكَّلْتُ عَلَى اللَّهِ لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ", 1, setOf(Window.OCCASION), "Sunan Abi Dawud 5095", "https://sunnah.com/abudawud:5095", Evidence.SAHIH_GRADED, "When leaving the home."),
        Practice("MASJID_ENTRY", "Entering masjid", "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ", 1, setOf(Window.OCCASION), "Sahih Muslim 713a", "https://sunnah.com/muslim:713a", Evidence.SAHIH, "When entering the mosque."),
        Practice("MASJID_EXIT", "Leaving masjid", "اللَّهُمَّ إِنِّي أَسْأَلُكَ مِنْ فَضْلِكَ", 1, setOf(Window.OCCASION), "Sahih Muslim 713a", "https://sunnah.com/muslim:713a", Evidence.SAHIH, "When leaving the mosque."),
        Practice("AFTER_ADHAN", "Dua after Adhan", "اللَّهُمَّ رَبَّ هَذِهِ الدَّعْوَةِ التَّامَّةِ وَالصَّلَاةِ الْقَائِمَةِ آتِ مُحَمَّدًا الْوَسِيلَةَ وَالْفَضِيلَةَ وَابْعَثْهُ مَقَامًا مَحْمُودًا الَّذِي وَعَدْتَهُ", 1, setOf(Window.OCCASION), "Sahih al-Bukhari 614", "https://sunnah.com/bukhari:614", Evidence.SAHIH, "After the call to prayer; track occurrences separately if repeated, not a compulsory daily quota."),
        Practice("BEFORE_FOOD", "Bismillah before food", "بِسْمِ اللَّهِ", 1, setOf(Window.OCCASION), "Sahih al-Bukhari 5376", "https://sunnah.com/bukhari:5376", Evidence.SAHIH, "The hadith includes saying Allah's name, eating with the right hand and what is near you."),
        Practice("AFTER_PRAYER_ISTIGHFAR", "Istighfar after Farz", "أَسْتَغْفِرُ اللَّهَ", 3, setOf(Window.AFTER_SALAH), "Sahih Muslim 591", "https://sunnah.com/muslim:591", Evidence.SAHIH, "Three times following a completed prayer, separate from the 1,200 daily private Istighfar goal."),
        Practice("AFTER_PRAYER_SALAM", "Allahumma antas-salam", "اللَّهُمَّ أَنْتَ السَّلَامُ وَمِنْكَ السَّلَامُ تَبَارَكْتَ ذَا الْجَلَالِ وَالْإِكْرَامِ", 1, setOf(Window.AFTER_SALAH), "Sahih Muslim 591", "https://sunnah.com/muslim:591", Evidence.SAHIH, "Post-prayer invocation."),
        Practice("AFTER_PRAYER_TASBIH", "Tasbih after prayer", "سُبْحَانَ اللَّهِ", 33, setOf(Window.AFTER_SALAH), "Sahih Muslim 597a", "https://sunnah.com/muslim:597a", Evidence.SAHIH, "One reported pattern: 33 times."),
        Practice("AFTER_PRAYER_TAHMID", "Tahmid after prayer", "الْحَمْدُ لِلَّهِ", 33, setOf(Window.AFTER_SALAH), "Sahih Muslim 597a", "https://sunnah.com/muslim:597a", Evidence.SAHIH, "One reported pattern: 33 times."),
        Practice("AFTER_PRAYER_TAKBIR", "Takbir after prayer", "اللَّهُ أَكْبَرُ", 33, setOf(Window.AFTER_SALAH), "Sahih Muslim 597a", "https://sunnah.com/muslim:597a", Evidence.SAHIH, "One reported pattern: 33 times, followed by a distinct concluding tahlil."),
        Practice("AFTER_PRAYER_TAHLIL", "Concluding Tahlil", "لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ", 1, setOf(Window.AFTER_SALAH), "Sahih Muslim 597a", "https://sunnah.com/muslim:597a", Evidence.SAHIH, "Completes one described 100-count post-prayer sequence."),
    )

    val habits = listOf(
        SunnahHabit("EAT_RIGHT_HAND", "Eat with right hand", "Sahih al-Bukhari 5376", "https://sunnah.com/bukhari:5376", "During meals; mark consciously practiced once or more."),
        SunnahHabit("EAT_NEAREST", "Eat from what is nearest", "Sahih al-Bukhari 5376", "https://sunnah.com/bukhari:5376", "Relevant when sharing a dish, not every meal."),
        SunnahHabit("RIGHT_SIDE_SLEEP", "Rest/sleep on right side", "Sahih al-Bukhari 6315", "https://sunnah.com/bukhari:6315", "Before sleep; context dependent."),
        SunnahHabit("SIWAK_NIGHT", "Use siwak when waking at night", "Sahih al-Bukhari 245", "https://sunnah.com/bukhari:245", "Context-dependent, only if waking at night."),
    )

    val practiceIds = practices.map { it.id }.toSet()
    val habitIds = habits.map { it.id }.toSet()

    init {
        check(practiceIds.size == practices.size)
        check(habitIds.size == habits.size)
        check(practices.all { it.target > 0 && it.windows.isNotEmpty() && it.sourceUrl.startsWith("https://") })
    }

    fun forWindow(window: Window): List<Practice> =
        practices.filter { window in it.windows }

    fun scope(window: Window): String = window.name

    // A voluntary recitation log is not an assertion that the whole practice
    // is required every day. Morning and evening have separate stored totals.
    fun targetCount(window: Window, includeDisputed: Boolean = false): Int =
        forWindow(window).count { includeDisputed || !it.disputed }
}
