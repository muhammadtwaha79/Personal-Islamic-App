package com.muhammadtwaha.ibadahcompanion.data

import com.muhammadtwaha.ibadahcompanion.data.db.MasnoonDao
import com.muhammadtwaha.ibadahcompanion.data.db.MunajatManzil
import com.muhammadtwaha.ibadahcompanion.domain.time.AppClock
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/** Keeps the owner's Munajat reading check-ins, without exposing the
 * removed Masnoon Duas catalog. The old Room table is preserved safely. */
@Singleton
class ManzilsRepository @Inject constructor(
    private val dao: MasnoonDao,
    private val clock: AppClock,
) {
    fun munajat(date: LocalDate) = dao.observeMunajat(date.toString())

    suspend fun saveMunajat(date: LocalDate, manzil: Int, edition: String, completed: Boolean) {
        require(!date.isAfter(clock.today()))
        require(manzil in 1..7)
        require(edition.length <= 100)
        dao.saveMunajat(MunajatManzil(date.toString(), manzil, edition.trim(),
            completed, clock.now().toEpochMilli()))
    }
}
