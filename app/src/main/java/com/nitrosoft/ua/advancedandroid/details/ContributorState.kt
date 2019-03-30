package com.nitrosoft.ua.advancedandroid.details

import androidx.annotation.Nullable
import com.nitrosoft.ua.advancedandroid.models.Contributor

data class ContributorState(
        val loading: Boolean,
        @Nullable var contributors: List<Contributor>? = null,
        @Nullable val errorRes: Int? = null) {

    fun isSuccess(): Boolean {
        return errorRes == null
    }
}

