package com.nitrosoft.ua.advancedandroid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.nitrosoft.ua.advancedandroid.data.DataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

abstract class RepoNetworkDataSourceBinder<DataType, NetworkRequestType>(dispatcher: CoroutineDispatcher) {

    companion object {
        //private val TAG: String = createTag(RepoNetworkDataSourceBinder::class.java.simpleName)
    }

    private var result: LiveData<out RepoState<DataType>> = liveData(dispatcher) {
        emit(RepoState.Loading())

        getDatabaseData().collect {
            emit(RepoState.Success(it))
        }

        /* getDatabaseData().collect {
             emit(RepoState.Loading(it))
             if (shouldFetch(it)) {
                 try {
                     val remoteData: DataType = getNetworkData()
                     saveRequest(remoteData)
                 } catch (e: Exception) {
                     when (e) {
                         is IOException -> {
                             emit(RepoState.NetworkError)
                         }
                         is HttpException -> {
                             emit(RepoState.GenericError(e.code(), null))
                         }
                         else -> {
                             emit(RepoState.GenericError(null, null))
                         }
                     }
                     onFetchFailed()
                 }
                 getDatabaseData().collect { data ->
                     emit(RepoState.Success(data))
                 }
             } else {
                 getDatabaseData().collect { data ->
                     emit(RepoState.Success(data))
                 }
             }
         }*/
    }

    protected abstract suspend fun getDatabaseData(): Flow<DataType>

    protected abstract suspend fun getNetworkData(): DataWrapper<NetworkRequestType>

    protected abstract suspend fun saveRequest(request: NetworkRequestType)

    protected abstract fun shouldFetch(data: DataType?): Boolean

    protected open fun onFetchFailed() {}

    fun asLiveData(): LiveData<out RepoState<DataType>> = result
}