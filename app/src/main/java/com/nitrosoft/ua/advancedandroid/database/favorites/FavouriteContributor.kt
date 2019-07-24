package com.nitrosoft.ua.advancedandroid.database.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_contributors")
data class FavouriteContributor(@PrimaryKey private val id: Long)