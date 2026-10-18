package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** Key-value table for technical application metadata. It holds no worship data. */
@Entity(tableName = "app_meta")
data class AppMetaEntity(
    @PrimaryKey @ColumnInfo(name = "meta_key") val metaKey: String,
    @ColumnInfo(name = "meta_value") val metaValue: String,
)
