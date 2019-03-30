package com.nitrosoft.ua.advancedandroid.details

import androidx.annotation.Nullable

data class RepoDetailsState(
        val loading: Boolean,
        @Nullable val name: String? = null,
        @Nullable val description: String? = null,
        @Nullable val createDate: String? = null,
        @Nullable val updateDate: String? = null,
        @Nullable val errorRes: Int? = null) {

    fun isSuccess(): Boolean {
        return errorRes == null
    }
}

