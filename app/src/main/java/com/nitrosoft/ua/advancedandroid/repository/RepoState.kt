package com.nitrosoft.ua.advancedandroid.repository

sealed class RepoState<T> {
    data class Success<T>(val value: T) : RepoState<T>()
    data class GenericError(val code: Int? = null, val errorData: Map<String, Any>? = null) : RepoState<Nothing>()
    object NetworkError : RepoState<Nothing>()
    data class Loading<T>(val value: T? = null) : RepoState<T>()
}
