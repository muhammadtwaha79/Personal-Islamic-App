package com.muhammadtwaha.ibadahcompanion.di

import android.content.Context
import androidx.room.Room
import com.muhammadtwaha.ibadahcompanion.data.db.AppDatabase
import com.muhammadtwaha.ibadahcompanion.data.db.AppMetaDao
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

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME).build()

    @Provides
    fun provideAppMetaDao(database: AppDatabase): AppMetaDao = database.appMetaDao()

    @Provides
    @Singleton
    fun provideClock(): AppClock = SystemAppClock()

    @Provides
    @Singleton
    fun provideIdGenerator(): IdGenerator = UuidIdGenerator()
}
