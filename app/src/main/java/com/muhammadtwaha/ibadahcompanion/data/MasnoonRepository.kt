package com.muhammadtwaha.ibadahcompanion.data

import com.muhammadtwaha.ibadahcompanion.data.db.DailySunnahCheck
import com.muhammadtwaha.ibadahcompanion.data.db.DhikrDao
import com.muhammadtwaha.ibadahcompanion.data.db.MasnoonDao
import com.muhammadtwaha.ibadahcompanion.data.db.MunajatManzil
import com.muhammadtwaha.ibadahcompanion.data.db.RecitationEvent
import com.muhammadtwaha.ibadahcompanion.domain.masnoon.MasnoonCatalog
import com.muhammadtwaha.ibadahcompanion.domain.masnoon.Window
import com.muhammadtwaha.ibadahcompanion.domain.time.AppClock
import com.muhammadtwaha.ibadahcompanion.domain.worship.Prayer
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/** Corrective event accounting reuses the existing append-only Dhikr journal.
 * Never infer religious compliance from unrecorded or unperformed acts.
 */
@Singleton
class MasnoonRepository @Inject constructor(
    private val dhikrDao: DhikrDao,
    private val masnoonDao: MasnoonDao,
    private val clock: AppClock,
) {
    fun totals(date: LocalDate) = dhikrDao.observeDayTotals(date.toString())
    fun habits(date: LocalDate) = masnoonDao.observeHabits(date.toString())
    fun munajat(date: LocalDate) = masnoonDao.observeMunajat(date.toString())

    suspend fun adjust(
        date: LocalDate, id: String, window: Window,
        delta: Int, prayer: Prayer? = null,
    ) {
        require(!date.isAfter(clock.today())) { "Future recitation cannot be recorded." }
        val entry = requireNotNull(MasnoonCatalog.practices.firstOrNull { it.id == id }) {
            "Unknown sourced practice ID"
        }
        require(window in entry.windows) { "Practice not valid for selected time period" }
        if (window == Window.AFTER_SALAH) requireNotNull(prayer)
        else require(prayer == null)
        require(delta in -1000..1000 && delta != 0)
        val scope = when (window) {
            Window.AFTER_SALAH -> "AFTER_SALAH_${prayer!!.name}"
            else -> window.name
        }
        dhikrDao.adjustCount(RecitationEvent(
            UUID.randomUUID().toString(), date.toString(), id, scope,
            delta, clock.now().toEpochMilli(),
        ))
    }

    suspend fun saveHabit(date: LocalDate, habitId: String, completed: Boolean) {
        require(!date.isAfter(clock.today())) { "Cannot log future Sunnah acts." }
        require(habitId in MasnoonCatalog.habitIds)
        masnoonDao.saveHabit(DailySunnahCheck(date.toString(), habitId,
            completed, clock.now().toEpochMilli()))
    }

    suspend fun saveMunajat(
        date: LocalDate, manzil: Int, edition: String, completed: Boolean,
    ) {
        require(!date.isAfter(clock.today())) { "Cannot complete future Munajat manzil." }
        require(manzil in 1..7)
        require(edition.length <= 100)
        masnoonDao.saveMunajat(MunajatManzil(date.toString(), manzil, edition.trim(),
            completed, clock.now().toEpochMilli()))
    }
}
