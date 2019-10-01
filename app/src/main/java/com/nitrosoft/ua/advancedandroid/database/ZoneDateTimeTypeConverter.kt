package com.nitrosoft.ua.advancedandroid.database

import androidx.room.TypeConverter
import org.threeten.bp.ZonedDateTime


class ZoneDateTimeTypeConverter {

    @TypeConverter
    fun fromString(str: String): ZonedDateTime {
        return ZonedDateTime.parse(str)
    }

    @TypeConverter
    fun toString(value: ZonedDateTime?): String? {
        return value?.toString()
    }
}