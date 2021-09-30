package com.muijp.hibi.database.memo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo ORDER BY formattedDate DESC")
    fun observeAll(): LiveData<List<Memo>>

    @Query("SELECT * FROM memo WHERE formattedDate = :formattedDate")
    suspend fun findByFormattedDate(formattedDate: String): Memo?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(memo: Memo)

    @Update
    suspend fun update(memo: Memo)

    @Delete
    suspend fun delete(memo: Memo)

    @Transaction
    suspend fun upsert(memo: Memo) {
        val existingMemo = findByFormattedDate(memo.formattedDate)
        if (existingMemo == null) {
            insert(memo)
        } else {
            update(memo)
        }
    }
}