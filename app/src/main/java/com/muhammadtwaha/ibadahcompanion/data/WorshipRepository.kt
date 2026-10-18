package com.muhammadtwaha.ibadahcompanion.data

import com.muhammadtwaha.ibadahcompanion.data.db.*
import com.muhammadtwaha.ibadahcompanion.domain.time.AppClock
import com.muhammadtwaha.ibadahcompanion.domain.worship.*
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorshipRepository @Inject constructor(
    private val dao: WorshipDao,
    private val appMetaDao: AppMetaDao,
    private val clock: AppClock,
) {
    fun salah(date: LocalDate) = dao.observeSalah(date.toString())
    fun challenge(start: LocalDate = WorshipRules.trackingStart) =
        dao.observeSalahRange(start.toString(), start.plusDays(39).toString())
    fun practices(date: LocalDate) = dao.observePractices(date.toString())
    fun quran(date: LocalDate) = dao.observeQuranDay(date.toString())
    fun quranPeriod(from: LocalDate, until: LocalDate) =
        dao.observeQuranRange(from.toString(), until.toString())

    fun khatamPeriod(from: LocalDate, until: LocalDate) =
        dao.observeKhatams(from.toString(), until.toString())

    suspend fun recordKhatam(date: LocalDate, completed: Boolean) {
        require(!date.isAfter(clock.today())) { "Future khatam completion is not allowed" }
        val delta = if (completed) 1 else -1
        dao.recordKhatam(QuranKhatamEvent(UUID.randomUUID().toString(),
            date.toString(), delta, clock.now().toEpochMilli()))
    }

    suspend fun setSalah(
        date: LocalDate, prayer: Prayer, status: PrayerStatus,
        congregation: Congregation, takbeer: Takbeer, joining: Joining = Joining.UNKNOWN,
        middayMode: MiddayMode = MiddayMode.UNKNOWN,
    ) {
        require(WorshipRules.valid(status, congregation, takbeer, joining)) {
            "Invalid Salah and congregation combination"
        }
        require(!date.isAfter(clock.today())) { "Future Salah completion is not allowed" }
        if (prayer != Prayer.DHUHR) require(middayMode == MiddayMode.UNKNOWN)
        val record = SalahRecord(date.toString(), prayer.name, status.name, congregation.name,
            takbeer.name, joining.name, middayMode.name, clock.now().toEpochMilli())
        if (dao.getSalah(date.toString(), prayer.name)?.let {
                it.copy(updatedAt = record.updatedAt) == record
            } == true) return
        val revision = SalahRevision(UUID.randomUUID().toString(), record.date, record.prayer,
            record.status, record.congregation, record.takbeer, record.joining, record.middayMode,
            record.updatedAt)
        dao.saveSalah(record, revision)
    }

    suspend fun setPractice(date: LocalDate, practice: String, rakahs: Int, completed: Boolean) {
        require(!date.isAfter(clock.today())) { "Future Sunnah/Nafl completion is not allowed" }
        require(practice in PRACTICES.map { it.key })
        require(rakahs in 1..20)
        dao.upsertPractice(PracticeRecord(date.toString(), practice, rakahs, completed,
            clock.now().toEpochMilli()))
    }

    suspend fun setQuran(
        date: LocalDate, deltaHalfUnits: Int = 0,
        yaseen: Boolean? = null, waqiah: Boolean? = null, mulk: Boolean? = null,
    ) {
        require(!date.isAfter(clock.today())) { "Future Quran reading is not allowed" }
        require(deltaHalfUnits in -1..1)
        dao.applyQuranChange(date.toString(), deltaHalfUnits, yaseen, waqiah, mulk,
            clock.now().toEpochMilli())
    }

    suspend fun setRamadanRange(from: LocalDate, until: LocalDate) {
        require(!until.isBefore(from) && !until.isAfter(from.plusDays(35))) {
            "Select a valid manually confirmed Ramadan date range (max 36 days)"
        }
        appMetaDao.put(AppMetaEntity("ramadan_from", from.toString()))
        appMetaDao.put(AppMetaEntity("ramadan_until", until.toString()))
    }
    suspend fun ramadanRange(): Pair<LocalDate, LocalDate>? {
        val from = appMetaDao.get("ramadan_from")?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
        val until = appMetaDao.get("ramadan_until")?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
        return if (from != null && until != null && !until.isBefore(from)) from to until else null
    }
}

data class PracticeDefinition(val key: String, val label: String, val defaultRakahs: Int)
val PRACTICES = listOf(
    PracticeDefinition("FAJR_SUNNAH", "Fajr — 2 Sunnah", 2),
    PracticeDefinition("DHUHR_BEFORE", "Dhuhr — 4 Sunnah before", 4),
    PracticeDefinition("DHUHR_AFTER", "Dhuhr — 2 Sunnah after", 2),
    PracticeDefinition("ASR_BEFORE", "Asr — 4 Sunnah before", 4),
    PracticeDefinition("MAGHRIB_AFTER", "Maghrib — 2 Sunnah after", 2),
    PracticeDefinition("ISHA_BEFORE", "Isha — 4 Sunnah before", 4),
    PracticeDefinition("ISHA_AFTER", "Isha — 2 Sunnah after", 2),
    PracticeDefinition("TAHAJJUD", "Tahajjud", 2),
    PracticeDefinition("ISHRAQ", "Ishraq", 2),
    PracticeDefinition("CHASHT", "Chasht / Duha", 2),
    PracticeDefinition("AWWABEEN", "Awwabeen", 2),
)
