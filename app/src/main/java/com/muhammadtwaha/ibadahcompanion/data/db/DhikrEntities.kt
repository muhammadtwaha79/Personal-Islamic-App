package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** Immutable event journal. The canonical count is the sum of signed changes. */
@Entity(tableName = "recitation_event")
data class RecitationEvent(
    @PrimaryKey @ColumnInfo(name = "event_id") val eventId: String,
    val date: String,
    @ColumnInfo(name = "item_id") val itemId: String,
    // Blank only for independent daily targets. Otherwise one of the five Farz categories.
    val prayer: String,
    val delta: Int,
    @ColumnInfo(name = "recorded_at") val recordedAt: Long,
)

data class RecitationTotal(
    val itemId: String,
    val prayer: String,
    val total: Long,
)

/** Dala'il editions vary. This is a user-chosen manzil (1..7) and completion checkbox.
 * The app does not reproduce any Dala'il book text or assert a canonical partition. */
@Entity(tableName = "dalail_daily")
data class DalailDaily(
    @PrimaryKey val date: String,
    val manzil: Int,
    val edition: String,
    val completed: Boolean,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)
