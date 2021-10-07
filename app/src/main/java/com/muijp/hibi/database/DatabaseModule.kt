package com.muijp.hibi.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        synchronized(AppDatabase::class.java) {
            Timber.d("database initialized")
            return Room
                .databaseBuilder(context, AppDatabase::class.java, "hibi_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}