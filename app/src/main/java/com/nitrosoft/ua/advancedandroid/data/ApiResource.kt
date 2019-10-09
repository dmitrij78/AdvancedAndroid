package com.nitrosoft.ua.advancedandroid.data

abstract class ApiResource<T>(
        val data: T? = null,
        val throwable: Throwable? = null) {
    class Success<T>(data: T) : ApiResource<T>(data)
    class Error<T>(throwable: Throwable?) : ApiResource<T>(null, throwable)
}