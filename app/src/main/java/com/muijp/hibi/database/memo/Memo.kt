package com.muijp.hibi.database.memo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime
import java.util.*

@Entity
data class Memo(
    @PrimaryKey val id: String,
    var text: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
) {
    companion object {
        fun new(text: String): Memo {
            val now = ZonedDateTime.now()
            return Memo(UUID.randomUUID().toString(), text, now, now)
        }
    }
}
