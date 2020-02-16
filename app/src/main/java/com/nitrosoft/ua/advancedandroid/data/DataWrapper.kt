package com.nitrosoft.ua.advancedandroid.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

sealed class DataWrapper<T>(
        val data: T? = null,
        val throwable: Throwable? = null) {
    class Success<T>(data: T) : DataWrapper<T>(data)
    class Error<T>(throwable: Throwable) : DataWrapper<T>(null, throwable)
}

suspend fun <T> fetchData(dispatcher: CoroutineDispatcher, block: suspend () -> T): DataWrapper<T> {
    return withContext(dispatcher) {
        try {
            DataWrapper.Success(block.invoke())
        } catch (throwable: Throwable) {
            DataWrapper.Error<T>(throwable)
        }
    }
}