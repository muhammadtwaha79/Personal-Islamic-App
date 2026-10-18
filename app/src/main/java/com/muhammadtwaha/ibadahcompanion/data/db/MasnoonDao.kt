package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MasnoonDao {
    @Query("SELECT * FROM daily_sunnah_check WHERE date = :date")
    fun observeHabits(date: String): Flow<List<DailySunnahCheck>>

    @Upsert
    suspend fun saveHabit(check: DailySunnahCheck)

    @Query("SELECT * FROM munajat_manzil WHERE date = :date LIMIT 1")
    fun observeMunajat(date: String): Flow<MunajatManzil?>

    @Upsert
    suspend fun saveMunajat(record: MunajatManzil)
}
