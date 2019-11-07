package com.nitrosoft.ua.advancedandroid.data

abstract class DataResource<T>(
        val data: T? = null,
        val throwable: Throwable? = null) {
    class Success<T>(data: T) : DataResource<T>(data)
    class Error<T>(throwable: Throwable) : DataResource<T>(null, throwable)
}