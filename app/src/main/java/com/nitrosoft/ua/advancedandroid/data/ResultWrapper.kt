package com.nitrosoft.ua.advancedandroid.data

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val errorData: Map<String, Any>? = null) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
    data class Loading<out T>(val value: T? = null) : ResultWrapper<T>()
}
