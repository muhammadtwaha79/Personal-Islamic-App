package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "salah_record", primaryKeys = ["date", "prayer"])
data class SalahRecord(
    val date: String,
    val prayer: String,
    val status: String,
    val congregation: String,
    val takbeer: String,
    val joining: String,
    @ColumnInfo(name = "midday_mode") val middayMode: String,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)

@Entity(tableName = "salah_revision")
data class SalahRevision(
    @PrimaryKey @ColumnInfo(name = "revision_id") val revisionId: String,
    val date: String,
    val prayer: String,
    val status: String,
    val congregation: String,
    val takbeer: String,
    val joining: String,
    @ColumnInfo(name = "midday_mode") val middayMode: String,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)

@Entity(tableName = "practice_record", primaryKeys = ["date", "practice"])
data class PracticeRecord(
    val date: String,
    val practice: String,
    val rakahs: Int,
    val completed: Boolean,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)

@Entity(tableName = "quran_khatam_event")
data class QuranKhatamEvent(
    @PrimaryKey val eventId: String,
    val date: String,
    val delta: Int,
    @ColumnInfo(name = "recorded_at") val recordedAt: Long,
)

@Entity(tableName = "quran_day")
data class QuranDay(
    @PrimaryKey val date: String,
    @ColumnInfo(name = "half_juz_units") val halfJuzUnits: Int,
    val yaseen: Boolean,
    val waqiah: Boolean,
    val mulk: Boolean,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)
