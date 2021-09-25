package com.muijp.hibi.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.muijp.hibi.database.AppDatabase
import com.muijp.hibi.database.memo.Memo

class MemoRepository(
    private val database: AppDatabase,
) {
    fun observeAll(): LiveData<List<Memo>> {
        return database.memoDao.observeAll()
    }

    suspend fun insert(memo: Memo) {
        return database.memoDao.insert(memo)
    }

    suspend fun update(memo: Memo) {
        return database.memoDao.update(memo)
    }

    suspend fun delete(memo: Memo) {
        return database.memoDao.delete(memo)
    }
}