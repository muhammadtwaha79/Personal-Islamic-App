package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import com.muhammadtwaha.ibadahcompanion.domain.dhikr.RecitationRules

@Dao
interface DhikrDao {
    @Query("""
        SELECT item_id AS itemId, prayer, SUM(delta) AS total
        FROM recitation_event WHERE date = :date
        GROUP BY item_id, prayer
    """)
    fun observeDayTotals(date: String): Flow<List<RecitationTotal>>

    @Query("""
        SELECT COALESCE(SUM(delta), 0) FROM recitation_event
        WHERE date = :date AND item_id = :itemId AND prayer = :prayer
    """)
    suspend fun count(date: String, itemId: String, prayer: String): Long

    @Insert
    suspend fun insertEvent(entry: RecitationEvent)

    /** Serial read/validate/insert, rollback on error. No destructive REPLACE or DELETE. */
    @Transaction
    suspend fun adjustCount(entry: RecitationEvent) {
        val current = count(entry.date, entry.itemId, entry.prayer)
        RecitationRules.nextCount(current, entry.delta)
        insertEvent(entry)
    }

    @Query("SELECT * FROM dalail_daily WHERE date = :date LIMIT 1")
    fun observeDalail(date: String): Flow<DalailDaily?>

    @Upsert
    suspend fun upsertDalail(day: DalailDaily)
}
