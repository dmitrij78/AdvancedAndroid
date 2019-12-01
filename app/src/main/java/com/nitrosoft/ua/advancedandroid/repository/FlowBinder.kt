package com.nitrosoft.ua.advancedandroid.repository

import com.nitrosoft.ua.advancedandroid.data.ResultWrapper
import com.nitrosoft.ua.advancedandroid.data.fetchData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

abstract class RepoNetworkDataSourceBinderFlow<ResultType, NetworkRequestType>(dispatcher: CoroutineDispatcher) {

    companion object {
        //private val TAG: String = createTag(RepoNetworkDataSourceBinder::class.java.simpleName)
    }

    private var result: Flow<RepoState<ResultType>> = flow {
        emit(RepoState.Loading())

        val databaseData = getDatabaseData()
        databaseData.collect {
            emit(RepoState.Loading(it))

            if (shouldFetch(it)) {
                val networkResult = fetchData(dispatcher) {
                    getNetworkData()
                }

                when (networkResult) {
                    is ResultWrapper.Success -> {
                        saveRequest(networkResult.data)
                    }
                    is ResultWrapper.Error -> {

                    }
                }


                getDatabaseData().collect { data ->
                    emit(RepoState.Success(data))
                }
            } else {
                getDatabaseData().collect { data ->
                    emit(RepoState.Success(data))
                }
            }
        }
    }

    protected abstract suspend fun getDatabaseData(): Flow<ResultType>

    protected abstract suspend fun getNetworkData(): NetworkRequestType

    protected abstract suspend fun saveRequest(request: NetworkRequestType?)

    protected abstract fun shouldFetch(data: ResultType): Boolean

    protected open fun onFetchFailed() {}

    fun getDataFlow(): Flow<RepoState<ResultType>> = result
}