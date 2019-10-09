package com.nitrosoft.ua.advancedandroid.database.repos

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "repositories")
data class RepoEntity(
        @PrimaryKey val id: Long,
        val name: String,
        val description: String,
        val userId: Long,
        val userLogin: String,
        val starGazersCount: Long,
        val forksCount: Long,
        val contributorsUrl: String,
        val createdDate: ZonedDateTime,
        val updatedDate: ZonedDateTime
)