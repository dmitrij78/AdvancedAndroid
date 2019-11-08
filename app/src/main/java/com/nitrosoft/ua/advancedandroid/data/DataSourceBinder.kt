package com.nitrosoft.ua.advancedandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.nitrosoft.ua.advancedandroid.base.createTag

abstract class DataSourceBinder<ResultType, RequestType> {

    private val result = MediatorLiveData<RepoState<ResultType>>()

    companion object {
        private val TAG: String = createTag(RepoLiveRepository::class.java.simpleName)
    }

    init {
        result.postValue(RepoState.Loading(null))

        @Suppress("LeakingThis")
        val dbSource: LiveData<DataResource<ResultType>> = loadFromDb()
        result.addSource(dbSource) { dbDataResource ->
            result.removeSource(dbSource)

            if (shouldFetch(dbDataResource.data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(loadFromDb()) { dataResource ->
                    if (dataResource is DataResource.Error) {
                        result.value = RepoState.Error(null, dataResource.throwable)
                    } else {
                        result.value = RepoState.Success(dataResource.data)
                    }
                }
            }


            /*if (shouldFetch(initialData)) {
                fetchData(dbSource)
            } else {
                result.addSource(loadFromDb()) { data ->
                    result.value = RepoState.Success(data)
                }
            }*/
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<DataResource<ResultType>>) {

        val netSource = createCall()

        result.addSource(dbSource) { dataResource ->
            result.value = RepoState.Loading(dataResource.data)
        }

        result.addSource(netSource) {
            result.removeSource(netSource)
        }

    }

    private fun fetchData(dbData: LiveData<ResultType>) {
        result.addSource(dbData) {
            result.value = RepoState.Loading(it)
        }

        /*val apiCall = createCall()
        result.addSource(apiCall) { response ->
            result.removeSource(dbData)
            result.removeSource(apiCall)
            when (response) {
                is DataResource.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    saveCallResult(response)

                    result.addSource(loadFromDb()) { dbResult ->
                        result.value = RepoState.Success(dbResult)
                    }
                }
                is DataResource.Error<*> -> {
                    onFetchFailed()
                    @Suppress("UNCHECKED_CAST")
                    val error = response as DataResource.Error<RequestType>
                    result.addSource(dbData) { dbResult ->
                        result.value = RepoState.Error(dbResult, error.throwable)
                    }
                }
            }
        }*/
    }

    protected abstract fun loadFromDb(): LiveData<DataResource<ResultType>>

    protected abstract fun createCall(): LiveData<DataResource<RequestType>>

    protected abstract fun saveCallResult(requestType: RequestType?)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected open fun onFetchFailed() {}

    fun asLiveData(): LiveData<RepoState<ResultType>> = result
}