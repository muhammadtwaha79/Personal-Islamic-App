package com.muhammadtwaha.ibadahcompanion.data

import com.muhammadtwaha.ibadahcompanion.data.db.DalailDaily
import com.muhammadtwaha.ibadahcompanion.data.db.DhikrDao
import com.muhammadtwaha.ibadahcompanion.data.db.RecitationEvent
import com.muhammadtwaha.ibadahcompanion.domain.dhikr.SalawatCatalog
import com.muhammadtwaha.ibadahcompanion.domain.time.AppClock
import com.muhammadtwaha.ibadahcompanion.domain.worship.Prayer
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/** Local-only recitation tracker: prayer-scoped counters never double count into the
 * independently requested daily 1,200 short-form Salawat target. */
@Singleton
class DhikrRepository @Inject constructor(
    private val dao: DhikrDao,
    private val clock: AppClock,
) {
    companion object {
        const val DAILY_ISTIGHFAR = "DAILY_ISTIGHFAR"
        const val DAILY_BISMILLAH = "DAILY_BISMILLAH"
    }

    fun totals(date: LocalDate) = dao.observeDayTotals(date.toString())
    fun dalail(date: LocalDate) = dao.observeDalail(date.toString())

    suspend fun addDaily(date: LocalDate, itemId: String, delta: Int) {
        require(itemId == SalawatCatalog.DAILY_SHORT_ID ||
            itemId == DAILY_ISTIGHFAR || itemId == DAILY_BISMILLAH)
        append(date, itemId, prayer = "", delta)
    }

    suspend fun addAfterPrayer(date: LocalDate, prayer: Prayer, itemId: String, delta: Int) {
        require(itemId in SalawatCatalog.ids) { "Unknown user-supplied Salawat ID" }
        append(date, itemId, prayer.name, delta)
    }

    private suspend fun append(date: LocalDate, itemId: String, prayer: String, delta: Int) {
        require(!date.isAfter(clock.today())) { "Future recitations cannot be recorded" }
        require(delta != 0 && delta in -1000..1000)
        dao.adjustCount(RecitationEvent(
            eventId = UUID.randomUUID().toString(),
            date = date.toString(),
            itemId = itemId,
            prayer = prayer,
            delta = delta,
            recordedAt = clock.now().toEpochMilli(),
        ))
    }

    suspend fun setDalail(
        date: LocalDate, manzil: Int, completed: Boolean,
        edition: String = "",
    ) {
        require(!date.isAfter(clock.today())) { "Future manzil cannot be marked complete" }
        require(manzil in 1..7) { "Choose manzil 1 through 7" }
        require(edition.length <= 100)
        dao.upsertDalail(DalailDaily(date.toString(), manzil, edition.trim(), completed,
            clock.now().toEpochMilli()))
    }
}
