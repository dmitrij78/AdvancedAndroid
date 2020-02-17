package com.nitrosoft.ua.advancedandroid.data

sealed class RepoState<T>(
        val data: T? = null,
        val throwable: Throwable? = null,
        val isLoading: Boolean? = null) {
    class Success<T>(data: T) : RepoState<T>(data)
    class Loading<T>(isLoading: Boolean) : RepoState<T>()
    class Syncing<T> : RepoState<T>()
    class Error<T>(error: Throwable) : RepoState<T>(throwable = error)
}