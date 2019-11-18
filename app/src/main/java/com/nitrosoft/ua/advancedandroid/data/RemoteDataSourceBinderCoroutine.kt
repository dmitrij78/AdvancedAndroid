package com.nitrosoft.ua.advancedandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import retrofit2.HttpException
import java.io.IOException

abstract class RemoteDataSourceBinderCoroutine<DataType>(dispatcher: CoroutineDispatcher) {

    companion object {
        //private val TAG: String = createTag(RemoteDataSourceBinderCoroutine::class.java.simpleName)
    }

    private var result: LiveData<ResultWrapper<DataType>> = liveData(dispatcher) {
        emit(ResultWrapper.Loading())

        loadFromDb().collect {
            emit(ResultWrapper.Loading(it))
            if (shouldFetch(it)) {
                try {
                    val remoteData: DataType = createDataSourceCall()
                    saveRequest(remoteData)
                } catch (e: Exception) {
                    when (e) {
                        is IOException -> {
                            emit(ResultWrapper.NetworkError)
                        }
                        is HttpException -> {
                            emit(ResultWrapper.GenericError(e.code(), null))
                        }
                        else -> {
                            emit(ResultWrapper.GenericError(null, null))
                        }
                    }
                    onFetchFailed()
                }
                loadFromDb().collect { data ->
                    emit(ResultWrapper.Success(data))
                }
            } else {
                loadFromDb().collect { data ->
                    emit(ResultWrapper.Success(data))
                }
            }
        }
    }

    protected abstract suspend fun loadFromDb(): Flow<DataType>

    protected abstract suspend fun createDataSourceCall(): DataType

    protected abstract suspend fun saveRequest(data: DataType?)

    protected abstract fun shouldFetch(data: DataType?): Boolean

    protected open fun onFetchFailed() {}

    fun asLiveData(): LiveData<ResultWrapper<DataType>> = result
}