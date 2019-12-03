package com.nitrosoft.ua.advancedandroid.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

sealed class ResultWrapper<T>(
        val data: T? = null,
        val throwable: Throwable? = null) {
    class Success<T>(data: T) : ResultWrapper<T>(data)
    class Error<T>(throwable: Throwable) : ResultWrapper<T>(null, throwable)
}

suspend fun <T> fetchData(dispatcher: CoroutineDispatcher, dataSourceCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(dataSourceCall.invoke())
        } catch (throwable: Throwable) {
            ResultWrapper.Error<T>(throwable)
        }
    }
}