package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Phase 1 database foundation. Schema export is on so migration tests can exist.
 * Never use destructive migration: data loss must be explicit and tested.
 */
@Database(entities = [AppMetaEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appMetaDao(): AppMetaDao

    companion object {
        const val NAME = "ibadah.db"
    }
}
