package com.muhammadtwaha.ibadahcompanion.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AppMetaEntity::class, SalahRecord::class, SalahRevision::class,
        PracticeRecord::class, QuranDay::class, QuranKhatamEvent::class,
        RecitationEvent::class, DalailDaily::class,
        DailySunnahCheck::class, MunajatManzil::class],
    version = 5,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appMetaDao(): AppMetaDao
    abstract fun worshipDao(): WorshipDao
    abstract fun dhikrDao(): DhikrDao
    abstract fun masnoonDao(): MasnoonDao
    companion object { const val NAME = "ibadah.db" }
}
