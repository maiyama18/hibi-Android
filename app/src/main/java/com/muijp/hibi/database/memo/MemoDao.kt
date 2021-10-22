package com.muijp.hibi.database.memo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo ORDER BY createdAt DESC LIMIT :limit")
    fun liveDataByLimit(limit: Int): LiveData<List<Memo>>

    @Query("SELECT COUNT(id) FROM memo")
    suspend fun count(): Long

    @Query("SELECT * FROM memo WHERE id = :id")
    suspend fun find(id: String): Memo?

    @Query("SELECT * FROM memo WHERE text LIKE :percentQuery ORDER BY createdAt DESC LIMIT :limit")
    suspend fun search(percentQuery: String, limit: Int): List<Memo>

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