package com.nitrosoft.ua.advancedandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MediatorLiveData<DataResource<ResultType>>()

    init {
        @Suppress("LeakingThis")
        val dbData = loadFromDb()
        result.addSource(dbData) { data ->
            result.removeSource(dbData)
            if (shouldFetch(data)) {
                result.value = DataResource.Loading()
                val apiCall = createCall()
                result.addSource(dbData) { newData ->
                    DataResource.Loading(newData)
                }
                result.addSource(apiCall) { response ->
                    result.removeSource(dbData)
                    result.removeSource(apiCall)
                    when (response) {
                        is ApiResource.Success<*> -> {
                            @Suppress("UNCHECKED_CAST")
                            val success = response as ApiResource.Success<RequestType>
                            val item: RequestType = success.data!!
                            saveCallResult(item)
                            result.addSource(loadFromDb()) { newData ->
                                result.value = DataResource.Success(newData)
                            }
                        }
                        is ApiResource.Error<*> -> {
                            onFetchFailed()
                            @Suppress("UNCHECKED_CAST")
                            val error = response as ApiResource.Error<RequestType>
                            result.addSource(dbData) { newData ->
                                result.value = DataResource.Error(newData, error.throwable)
                            }
                        }
                    }
                }
            } else {
                result.addSource(loadFromDb()) { newData ->
                    result.value = DataResource.Success(newData)
                }
            }
        }
    }

    protected abstract fun loadFromDb(): LiveData<ResultType>

    protected abstract fun saveCallResult(requestType: RequestType?)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<RequestType>

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    protected open fun onFetchFailed() {}

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    fun asLiveData(): LiveData<DataResource<ResultType>> = result
}