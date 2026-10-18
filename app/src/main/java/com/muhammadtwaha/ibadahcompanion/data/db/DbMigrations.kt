package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/** Explicit, non-destructive upgrade from the previously shipped Phase 1 Room schema. */
object DbMigrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `salah_record` (
                    `date` TEXT NOT NULL, `prayer` TEXT NOT NULL,
                    `status` TEXT NOT NULL, `congregation` TEXT NOT NULL,
                    `takbeer` TEXT NOT NULL, `joining` TEXT NOT NULL,
                    `midday_mode` TEXT NOT NULL, `updated_at` INTEGER NOT NULL,
                    PRIMARY KEY(`date`, `prayer`)
                )
            """.trimIndent())
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `salah_revision` (
                    `revision_id` TEXT NOT NULL, `date` TEXT NOT NULL,
                    `prayer` TEXT NOT NULL, `status` TEXT NOT NULL,
                    `congregation` TEXT NOT NULL, `takbeer` TEXT NOT NULL,
                    `joining` TEXT NOT NULL, `midday_mode` TEXT NOT NULL,
                    `updated_at` INTEGER NOT NULL,
                    PRIMARY KEY(`revision_id`)
                )
            """.trimIndent())
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `practice_record` (
                    `date` TEXT NOT NULL, `practice` TEXT NOT NULL,
                    `rakahs` INTEGER NOT NULL, `completed` INTEGER NOT NULL,
                    `updated_at` INTEGER NOT NULL,
                    PRIMARY KEY(`date`, `practice`)
                )
            """.trimIndent())
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `quran_day` (
                    `date` TEXT NOT NULL, `half_juz_units` INTEGER NOT NULL,
                    `yaseen` INTEGER NOT NULL, `waqiah` INTEGER NOT NULL,
                    `mulk` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL,
                    PRIMARY KEY(`date`)
                )
            """.trimIndent())
        }
    }
}
