package com.nitrosoft.ua.advancedandroid.details

import androidx.annotation.Nullable

data class ContributorState(
        val loading: Boolean,
        @Nullable val errorRes: Int? = null) {

    fun isSuccess(): Boolean {
        return errorRes == null
    }
}

