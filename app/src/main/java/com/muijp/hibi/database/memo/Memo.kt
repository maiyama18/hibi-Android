package com.muijp.hibi.database.memo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(
    @PrimaryKey val formattedDate: String,
    var memo: String,
)
