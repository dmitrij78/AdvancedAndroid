package com.nitrosoft.ua.advancedandroid.models

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class User(
        val id: Long,
        val login: String
)