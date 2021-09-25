package com.muijp.hibi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.database.memo.MemoDao
import timber.log.Timber

@Database(entities = [Memo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val memoDao: MemoDao
}

private lateinit var database: AppDatabase

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::database.isInitialized) {
            database = Room
                .databaseBuilder(context, AppDatabase::class.java, "app_database")
                .build()
            Timber.d("database initialized")
        }
    }
    return database
}