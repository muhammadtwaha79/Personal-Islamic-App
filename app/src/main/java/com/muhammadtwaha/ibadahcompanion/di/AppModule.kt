package com.muhammadtwaha.ibadahcompanion.di

import android.content.Context
import androidx.room.Room
import com.muhammadtwaha.ibadahcompanion.data.db.AppDatabase
import com.muhammadtwaha.ibadahcompanion.data.db.AppMetaDao
import com.muhammadtwaha.ibadahcompanion.data.db.DbMigrations
import com.muhammadtwaha.ibadahcompanion.data.db.DhikrDao
import com.muhammadtwaha.ibadahcompanion.data.db.WorshipDao
import com.muhammadtwaha.ibadahcompanion.domain.id.IdGenerator
import com.muhammadtwaha.ibadahcompanion.domain.id.UuidIdGenerator
import com.muhammadtwaha.ibadahcompanion.domain.time.AppClock
import com.muhammadtwaha.ibadahcompanion.domain.time.SystemAppClock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun database(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME)
            .addMigrations(DbMigrations.MIGRATION_1_2, DbMigrations.MIGRATION_2_3, DbMigrations.MIGRATION_3_4).build()

    @Provides fun appMetaDao(database: AppDatabase): AppMetaDao = database.appMetaDao()
    @Provides fun worshipDao(database: AppDatabase): WorshipDao = database.worshipDao()
    @Provides fun dhikrDao(database: AppDatabase): DhikrDao = database.dhikrDao()
    @Provides @Singleton fun clock(): AppClock = SystemAppClock()
    @Provides @Singleton fun idGenerator(): IdGenerator = UuidIdGenerator()
}
