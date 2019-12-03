package com.nitrosoft.ua.advancedandroid.data

sealed class RepoState<T>(
        val data: T? = null,
        val error: Throwable? = null) {
    class Success<T>(data: T?) : RepoState<T>(data)
    class Loading<T>(data: T? = null) : RepoState<T>(data)
    class Error<T>(error: Throwable?) : RepoState<T>(null, error)
}