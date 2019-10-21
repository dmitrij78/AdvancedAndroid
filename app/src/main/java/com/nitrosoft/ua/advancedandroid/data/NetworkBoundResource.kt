package com.nitrosoft.ua.advancedandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MediatorLiveData<DataResource<ResultType>>()

    init {
        result.value = DataResource.Loading()

        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { initialData ->
            result.removeSource(dbSource)

            if (shouldFetch(initialData)) {
                fetchData(dbSource)
            } else {
                result.addSource(loadFromDb()) { data ->
                    result.value = DataResource.Success(data)
                }
            }
        }
    }

    private fun fetchData(dbData: LiveData<ResultType>) {
        result.addSource(dbData) {
            result.value = DataResource.Loading(it)
        }

        val apiCall = createCall()
        result.addSource(apiCall) { response ->
            result.removeSource(dbData)
            result.removeSource(apiCall)
            when (response) {
                is ApiResource.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    saveCallResult(response)

                    result.addSource(loadFromDb()) { dbResult ->
                        result.value = DataResource.Success(dbResult)
                    }
                }
                is ApiResource.Error<*> -> {
                    onFetchFailed()
                    @Suppress("UNCHECKED_CAST")
                    val error = response as ApiResource.Error<RequestType>
                    result.addSource(dbData) { dbResult ->
                        result.value = DataResource.Error(dbResult, error.throwable)
                    }
                }
            }
        }
    }

    protected abstract fun loadFromDb(): LiveData<ResultType>

    protected abstract fun saveCallResult(requestType: RequestType?)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<RequestType>

    protected open fun onFetchFailed() {}

    fun asLiveData(): LiveData<DataResource<ResultType>> = result
}