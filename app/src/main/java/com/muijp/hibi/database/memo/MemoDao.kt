package com.muijp.hibi.database.memo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo ORDER BY createdAt DESC")
    fun observeAll(): LiveData<List<Memo>>

    @Query("SELECT * FROM memo WHERE id = :id")
    suspend fun find(id: String): Memo?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(memo: Memo): Long

    @Update
    suspend fun update(memo: Memo)

    @Delete
    suspend fun delete(memo: Memo)

    @Transaction
    suspend fun upsert(memo: Memo) {
        if (insert(memo) == -1L) {
            update(memo)
        }
    }
}