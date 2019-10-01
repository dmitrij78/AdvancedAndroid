package com.nitrosoft.ua.advancedandroid.database.repos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nitrosoft.ua.advancedandroid.database.ZoneDateTimeTypeConverter
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "repositories")
data class Repository(
        @PrimaryKey val id: Long,
        val name: String,
        val description: String,
        val userId: Long,
        val userLogin: String,
        val starGazersCount: Long,
        val forksCount: Long,
        val contributorsUrl: String,
        @TypeConverters(ZoneDateTimeTypeConverter::class) val createdDate: ZonedDateTime,
        @TypeConverters(ZoneDateTimeTypeConverter::class) val updatedDate: ZonedDateTime
)