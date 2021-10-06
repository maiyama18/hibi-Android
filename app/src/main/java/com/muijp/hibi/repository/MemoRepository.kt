package com.muijp.hibi.repository

import androidx.lifecycle.LiveData
import com.muijp.hibi.database.AppDatabase
import com.muijp.hibi.database.memo.Memo

class MemoRepository(
    private val database: AppDatabase,
) {
    fun observe(limit: Int): LiveData<List<Memo>> {
        return database.memoDao.observe(limit)
    }

    suspend fun count(): Long {
        return database.memoDao.count()
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