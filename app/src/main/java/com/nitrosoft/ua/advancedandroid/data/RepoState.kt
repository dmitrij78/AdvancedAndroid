package com.nitrosoft.ua.advancedandroid.data

sealed class RepoState<T> {
    class Success<T>(val data: T) : RepoState<T>()
    class Loading<T>(val isLoading: Boolean) : RepoState<T>()
    class Syncing<T>(val isSyncing: Boolean) : RepoState<T>()
    class Error<T>(val error: Throwable) : RepoState<T>()
}