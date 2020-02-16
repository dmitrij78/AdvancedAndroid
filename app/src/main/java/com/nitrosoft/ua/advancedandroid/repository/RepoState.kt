package com.nitrosoft.ua.advancedandroid.repository

sealed class RepoState<T> {
    data class Success<T>(val value: T) : RepoState<T>()
    data class Error(val throwable: Throwable?) : RepoState<Nothing>()
    data class Loading<T>(val value: T? = null) : RepoState<T>()
}
