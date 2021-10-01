package com.muijp.hibi.repository

import androidx.lifecycle.LiveData
import com.muijp.hibi.database.AppDatabase
import com.muijp.hibi.database.memo.Memo

class MemoRepository(
    private val database: AppDatabase,
) {
    fun observeAll(): LiveData<List<Memo>> {
        return database.memoDao.observeAll()
    }

    suspend fun find(id: String): Memo? {
        return database.memoDao.find(id)
    }

    suspend fun search(query: String): List<Memo> {
        return database.memoDao.search("%${query}%")
    }

    suspend fun upsert(memo: Memo) {
        return database.memoDao.upsert(memo)
    }

    suspend fun delete(memo: Memo) {
        return database.memoDao.delete(memo)
    }
}