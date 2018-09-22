package com.nitrosoft.ua.advancedandroid.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.ZonedDateTime

class ZoneDatetimeAdapter {

    @FromJson
    fun fromJson(json: String): ZonedDateTime {
        return ZonedDateTime.parse(json)
    }

    @ToJson
    fun toJson(value: ZonedDateTime?): String? {
        return value?.toString()
    }
}