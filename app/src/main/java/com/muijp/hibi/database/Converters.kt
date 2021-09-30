package com.muijp.hibi.database

import androidx.room.TypeConverter
import java.time.ZonedDateTime

class Converters {
    @TypeConverter
    fun dateTimeToString(dateTime: ZonedDateTime?): String? {
        return dateTime?.toString()
    }

    @TypeConverter
    fun stringToDateTime(string: String?): ZonedDateTime? {
        if (string == null) { return null }
        return ZonedDateTime.parse(string)
    }
}