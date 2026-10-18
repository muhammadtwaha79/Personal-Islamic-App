package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/** Explicit, non-destructive upgrade from the previously shipped Phase 1 Room schema. */
object DbMigrations {
    /** New daily Sunnah/Munajat tables; never clear existing worship records. */
    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `daily_sunnah_check` (
                    `date` TEXT NOT NULL, `habit_id` TEXT NOT NULL,
                    `completed` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL,
                    PRIMARY KEY(`date`, `habit_id`)
                )
            """.trimIndent())
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `munajat_manzil` (
                    `date` TEXT NOT NULL, `manzil` INTEGER NOT NULL,
                    `edition` TEXT NOT NULL, `completed` INTEGER NOT NULL,
                    `updated_at` INTEGER NOT NULL,
                    PRIMARY KEY(`date`)
                )
            """.trimIndent())
        }
    }

    /** Add the append-only recitation journal and optional Dala'il day selection. */
    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `recitation_event` (
                    `event_id` TEXT NOT NULL, `date` TEXT NOT NULL,
                    `item_id` TEXT NOT NULL, `prayer` TEXT NOT NULL,
                    `delta` INTEGER NOT NULL, `recorded_at` INTEGER NOT NULL,
                    PRIMARY KEY(`event_id`)
                )
            """.trimIndent())
            db.execSQL("""
                CREATE INDEX IF NOT EXISTS `index_recitation_event_date_item_id_prayer`
                ON `recitation_event` (`date`, `item_id`, `prayer`)
            """.trimIndent())
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `dalail_daily` (
                    `date` TEXT NOT NULL, `manzil` INTEGER NOT NULL,
                    `edition` TEXT NOT NULL, `completed` INTEGER NOT NULL,
                    `updated_at` INTEGER NOT NULL,
                    PRIMARY KEY(`date`)
                )
            """.trimIndent())
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `quran_khatam_event` (
                    `eventId` TEXT NOT NULL, `date` TEXT NOT NULL,
                    `delta` INTEGER NOT NULL, `recorded_at` INTEGER NOT NULL,
                    PRIMARY KEY(`eventId`)
                )
            """.trimIndent())
        }
    }

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
