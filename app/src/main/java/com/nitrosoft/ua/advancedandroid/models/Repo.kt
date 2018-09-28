package com.nitrosoft.ua.advancedandroid.models

import com.squareup.moshi.Json
import org.threeten.bp.ZonedDateTime
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Repo(
        val id: Long,
        val name: String,
        val description: String,
        @Json(name = "owner") val user: User,
        @Json(name = "stargazers_count") val starGazersCount: Long,
        @Json(name = "forks_count") val forksCount: Long,
        @Json(name = "contributors_url") val contributorsUrl: String,
        @Json(name = "created_at") val createdDate: ZonedDateTime,
        @Json(name = "updated_at") val updatedDate: ZonedDateTime
)