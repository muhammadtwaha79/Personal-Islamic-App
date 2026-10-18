package com.muhammadtwaha.ibadahcompanion.domain.worship

import java.time.LocalDate
import java.time.temporal.ChronoUnit

enum class Prayer { FAJR, DHUHR, ASR, MAGHRIB, ISHA }
enum class PrayerStatus { PENDING, COMPLETED, MISSED, UNKNOWN }
enum class Congregation { UNKNOWN, WITH_JAMAAT, INDIVIDUAL }
enum class Takbeer { UNKNOWN, ACHIEVED, NOT_ACHIEVED, NOT_APPLICABLE }
enum class Joining { UNKNOWN, FROM_BEGINNING, JOINED_LATE }
enum class MiddayMode { UNKNOWN, DHUHR, JUMUAH }

/** Records describe self-reported activity, not a religious ruling about its validity. */
object WorshipRules {
    val trackingStart: LocalDate = LocalDate.of(2026, 10, 20)
    val fivePrayers: List<Prayer> = Prayer.entries.toList()
    const val challengeLength = 40
    const val ramadanKhatamTarget = 5
    const val halfJuzUnitsPerKhatam = 60

    fun valid(status: PrayerStatus, congregation: Congregation, takbeer: Takbeer, joining: Joining): Boolean {
        if (status != PrayerStatus.COMPLETED) {
            return congregation == Congregation.UNKNOWN && takbeer == Takbeer.UNKNOWN && joining == Joining.UNKNOWN
        }
        if (congregation == Congregation.INDIVIDUAL) {
            return takbeer == Takbeer.NOT_APPLICABLE && joining == Joining.UNKNOWN
        }
        if (congregation == Congregation.UNKNOWN) {
            return takbeer == Takbeer.UNKNOWN && joining == Joining.UNKNOWN
        }
        if (joining == Joining.JOINED_LATE && takbeer == Takbeer.ACHIEVED) return false
        return takbeer != Takbeer.NOT_APPLICABLE
    }

    fun challengeDay(date: LocalDate, start: LocalDate = trackingStart): Int? {
        val index = ChronoUnit.DAYS.between(start, date)
        return if (index in 0 until challengeLength) index.toInt() + 1 else null
    }
}

data class ChallengePrayer(val date: LocalDate, val prayer: Prayer, val achieved: Boolean)

data class ChallengeSummary(
    val achievedOpportunities: Int,
    val fullSuccessDays: Int,
    val longestConsecutiveDays: Int,
    val currentConsecutiveDays: Int,
    val remainingOpportunities: Int,
    val consideredDays: Int,
)

/** Pure, deterministic 40-day personal tracking metrics; never a spiritual verdict. */
object ChallengeCalculator {
    fun calculate(
        records: List<ChallengePrayer>,
        today: LocalDate,
        start: LocalDate = WorshipRules.trackingStart,
    ): ChallengeSummary {
        val end = start.plusDays(WorshipRules.challengeLength.toLong() - 1)
        val distinct = records.filter { it.achieved && it.date in start..end }
            .map { it.date to it.prayer }.toSet()
        val elapsed = (java.time.temporal.ChronoUnit.DAYS.between(start, today) + 1)
            .coerceIn(0, WorshipRules.challengeLength.toLong()).toInt()
        var full = 0
        var running = 0
        var longest = 0
        repeat(elapsed) { offset ->
            val date = start.plusDays(offset.toLong())
            if (WorshipRules.fivePrayers.all { (date to it) in distinct }) {
                full++
                running++
                longest = maxOf(longest, running)
            } else {
                running = 0
            }
        }
        return ChallengeSummary(
            achievedOpportunities = distinct.size,
            fullSuccessDays = full,
            longestConsecutiveDays = longest,
            currentConsecutiveDays = running,
            remainingOpportunities = WorshipRules.challengeLength * 5 - distinct.size,
            consideredDays = elapsed,
        )
    }
}

object QuranRules {
    const val dailyTargetHalfJuzUnits = 1
    const val ramadanTargetHalfJuzUnits = 300 // 5 x 30 juz x 2 half-juz units
    fun progressPercent(units: Int, target: Int): Int =
        if (target <= 0) 0 else (units.coerceAtLeast(0).toLong() * 100 / target).coerceAtMost(100).toInt()
}
