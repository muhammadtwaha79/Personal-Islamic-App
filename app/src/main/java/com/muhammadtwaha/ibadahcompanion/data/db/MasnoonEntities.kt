package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

/** Distinct from prayer performance, qaza accounting, and dhikr recitation events.
 * Checkbox only means the user says they practiced this habit at least once that day.
 */
@Entity(tableName = "daily_sunnah_check", primaryKeys = ["date", "habit_id"])
data class DailySunnahCheck(
    val date: String,
    @ColumnInfo(name = "habit_id") val habitId: String,
    val completed: Boolean,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)

/** A MANUAL reading tracker; no copyrighted text, no automatic weekday-to-manzil assumption. */
@Entity(tableName = "munajat_manzil")
data class MunajatManzil(
    @androidx.room.PrimaryKey val date: String,
    val manzil: Int,
    val edition: String,
    val completed: Boolean,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)
