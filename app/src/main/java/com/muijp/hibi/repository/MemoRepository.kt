package com.muijp.hibi.repository

import androidx.lifecycle.LiveData
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.database.memo.MemoDao
import javax.inject.Inject

class MemoRepository @Inject constructor(
    private val memoDao: MemoDao,
) {
    fun liveDataByLimit(limit: Int): LiveData<List<Memo>> {
        return memoDao.liveDataByLimit(limit)
    }

    suspend fun count(): Long {
        return memoDao.count()
    }

    suspend fun find(id: String): Memo? {
        return memoDao.find(id)
    }

    suspend fun search(query: String): List<Memo> {
        return memoDao.search("%${query}%")
    }

    suspend fun upsert(memo: Memo) {
        return memoDao.upsert(memo)
    }

    suspend fun delete(memo: Memo) {
        return memoDao.delete(memo)
    }
}