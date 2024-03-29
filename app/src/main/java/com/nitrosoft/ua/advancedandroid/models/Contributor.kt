package com.nitrosoft.ua.advancedandroid.models

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Contributor(
        val id: Long,
        val login: String,
        @Json(name = "avatar_url") val avatarUrl: String
)