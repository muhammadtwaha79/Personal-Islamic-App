package com.muhammadtwaha.ibadahcompanion.domain

import com.muhammadtwaha.ibadahcompanion.domain.worship.*
import java.time.LocalDate
import org.junit.Assert.*
import org.junit.Test

class WorshipRulesTest {
    private val start = LocalDate.of(2026, 10, 20)

    @Test fun challengeDaysAreStrictlyForty() {
        assertNull(WorshipRules.challengeDay(start.minusDays(1)))
        assertEquals(1, WorshipRules.challengeDay(start))
        assertEquals(40, WorshipRules.challengeDay(start.plusDays(39)))
        assertNull(WorshipRules.challengeDay(start.plusDays(40)))
        assertEquals(LocalDate.of(2026, 11, 28), start.plusDays(39))
    }

    @Test fun onlyJamaatAllowsTakbeerAchievement() {
        assertTrue(WorshipRules.valid(PrayerStatus.COMPLETED, Congregation.WITH_JAMAAT,
            Takbeer.ACHIEVED, Joining.FROM_BEGINNING))
        assertFalse(WorshipRules.valid(PrayerStatus.COMPLETED, Congregation.INDIVIDUAL,
            Takbeer.ACHIEVED, Joining.UNKNOWN))
        assertTrue(WorshipRules.valid(PrayerStatus.COMPLETED, Congregation.INDIVIDUAL,
            Takbeer.NOT_APPLICABLE, Joining.UNKNOWN))
        assertFalse(WorshipRules.valid(PrayerStatus.COMPLETED, Congregation.WITH_JAMAAT,
            Takbeer.ACHIEVED, Joining.JOINED_LATE))
        assertFalse(WorshipRules.valid(PrayerStatus.MISSED, Congregation.WITH_JAMAAT,
            Takbeer.UNKNOWN, Joining.UNKNOWN))
    }

    @Test fun noUnansweredPrayerIsCountedAsSuccess() {
        val four = Prayer.entries.take(4).map { ChallengePrayer(start, it, true) }
        val summary = ChallengeCalculator.calculate(four, start)
        assertEquals(4, summary.achievedOpportunities)
        assertEquals(0, summary.fullSuccessDays)
        assertEquals(0, summary.longestConsecutiveDays)
        assertEquals(196, summary.remainingOpportunities)
    }

    @Test fun duplicatesAndOutsideChallengeAreIgnored() {
        val dayOne = Prayer.entries.map { ChallengePrayer(start, it, true) }
        val extraneous = listOf(
            ChallengePrayer(start.minusDays(1), Prayer.FAJR, true),
            ChallengePrayer(start.plusDays(40), Prayer.FAJR, true),
        )
        val summary = ChallengeCalculator.calculate(dayOne + dayOne + extraneous, start.plusDays(1))
        assertEquals(5, summary.achievedOpportunities)
        assertEquals(1, summary.fullSuccessDays)
        assertEquals(1, summary.longestConsecutiveDays)
    }

    @Test fun historicalCorrectionRecalculatesConsecutiveDays() {
        val fullFirstTwo = (0L..1L).flatMap { day ->
            Prayer.entries.map { ChallengePrayer(start.plusDays(day), it, true) }
        }
        assertEquals(2, ChallengeCalculator.calculate(fullFirstTwo, start.plusDays(1)).longestConsecutiveDays)
        val withoutOne = fullFirstTwo.filterNot { it.date == start && it.prayer == Prayer.FAJR }
        assertEquals(1, ChallengeCalculator.calculate(withoutOne, start.plusDays(1)).longestConsecutiveDays)
        assertEquals(1, ChallengeCalculator.calculate(withoutOne, start.plusDays(1)).fullSuccessDays)
    }

    @Test fun quranHalfParaAndFiveKhatamTargets() {
        assertEquals(1, QuranRules.dailyTargetHalfJuzUnits)
        assertEquals(300, QuranRules.ramadanTargetHalfJuzUnits)
        assertEquals(0, QuranRules.progressPercent(0, 300))
        assertEquals(50, QuranRules.progressPercent(150, 300))
        assertEquals(100, QuranRules.progressPercent(400, 300))
    }
}
