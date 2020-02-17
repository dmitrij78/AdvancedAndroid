package com.nitrosoft.ua.advancedandroid.data

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

sealed class DataWrapper<T>(
        val data: T? = null,
        val throwable: Throwable? = null) {
    class Success<T>(data: T) : DataWrapper<T>(data)
    class Error<T>(throwable: Throwable) : DataWrapper<T>(null, throwable)
}

suspend fun <T> fetchData(coroutineContext: CoroutineContext, block: suspend () -> T): DataWrapper<T> {
    return withContext(coroutineContext) {
        try {
            DataWrapper.Success(block.invoke())
        } catch (throwable: Throwable) {
            DataWrapper.Error<T>(throwable)
        }
    }
}