package com.nitrosoft.ua.advancedandroid.data

abstract class DataResource<T>(
        val data: T? = null,
        val throwable: Throwable? = null) {
    class Success<T>(data: T?) : DataResource<T>(data)
    class Loading<T>(data: T? = null) : DataResource<T>(data)
    class Error<T>(data: T? = null, throwable: Throwable?) : DataResource<T>(data, throwable)
}