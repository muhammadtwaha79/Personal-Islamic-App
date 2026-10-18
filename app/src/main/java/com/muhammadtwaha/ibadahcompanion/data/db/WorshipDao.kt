package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WorshipDao {
    @Query("SELECT * FROM salah_record WHERE date = :date ORDER BY prayer")
    fun observeSalah(date: String): Flow<List<SalahRecord>>

    @Query("SELECT * FROM salah_record WHERE date >= :from AND date <= :until")
    fun observeSalahRange(from: String, until: String): Flow<List<SalahRecord>>

    @Query("SELECT * FROM salah_record WHERE date = :date AND prayer = :prayer LIMIT 1")
    suspend fun getSalah(date: String, prayer: String): SalahRecord?

    @Upsert
    suspend fun upsertSalah(record: SalahRecord)

    @Insert
    suspend fun insertSalahRevision(revision: SalahRevision)

    @Transaction
    suspend fun saveSalah(record: SalahRecord, revision: SalahRevision) {
        upsertSalah(record)
        insertSalahRevision(revision)
    }

    @Query("SELECT * FROM practice_record WHERE date = :date ORDER BY practice")
    fun observePractices(date: String): Flow<List<PracticeRecord>>

    @Upsert
    suspend fun upsertPractice(record: PracticeRecord)

    @Query("SELECT * FROM quran_day WHERE date = :date LIMIT 1")
    fun observeQuranDay(date: String): Flow<QuranDay?>

    @Query("SELECT * FROM quran_day WHERE date = :date LIMIT 1")
    suspend fun getQuranDay(date: String): QuranDay?

    @Query("SELECT * FROM quran_day WHERE date >= :from AND date <= :until")
    fun observeQuranRange(from: String, until: String): Flow<List<QuranDay>>

    @Upsert
    suspend fun upsertQuranDay(record: QuranDay)
}
