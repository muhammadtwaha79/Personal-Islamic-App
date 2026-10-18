package com.muhammadtwaha.ibadahcompanion.domain.dhikr

/** Safe boundaries for a self-reported recitation event journal.
 * This counts acts performed; it neither verifies spiritual reward nor imposes obligations. */
object RecitationRules {
    const val MAX_ABSOLUTE_ADJUSTMENT = 1000
    const val MAX_STORED_COUNT = 1_000_000L

    fun nextCount(current: Long, delta: Int): Long {
        require(current >= 0 && current <= MAX_STORED_COUNT)
        require(delta != 0 && delta in -MAX_ABSOLUTE_ADJUSTMENT..MAX_ABSOLUTE_ADJUSTMENT)
        val next = current + delta
        require(next in 0..MAX_STORED_COUNT) { "Not enough prior repetitions to undo" }
        return next
    }

    fun completionPercent(count: Long, target: Int): Int {
        require(target > 0)
        return ((count.coerceAtLeast(0).coerceAtMost(target.toLong()) * 100) / target).toInt()
    }

    /** After-Fard 10x counts are independent of the 1,200x short-Salawat daily goal. */
    fun prayerFormulationGoals() = SalawatCatalog.afterFard.size * 5
}
