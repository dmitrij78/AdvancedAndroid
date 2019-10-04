package com.nitrosoft.ua.advancedandroid.database

import androidx.room.TypeConverter
import org.threeten.bp.ZonedDateTime


object ZoneDateTimeTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(str: String): ZonedDateTime {
        return ZonedDateTime.parse(str)
    }

    @TypeConverter
    @JvmStatic
    fun toString(value: ZonedDateTime?): String? {
        return value?.toString()
    }
}