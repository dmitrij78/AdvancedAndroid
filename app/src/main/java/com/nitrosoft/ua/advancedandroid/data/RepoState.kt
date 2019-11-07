package com.nitrosoft.ua.advancedandroid.data

abstract class RepoState<T>(
        val data: T? = null,
        val throwable: Throwable? = null) {
    class Success<T>(data: T?) : RepoState<T>(data)
    class Loading<T>(data: T? = null) : RepoState<T>(data)
    class Error<T>(data: T? = null, throwable: Throwable?) : RepoState<T>(data, throwable)
}