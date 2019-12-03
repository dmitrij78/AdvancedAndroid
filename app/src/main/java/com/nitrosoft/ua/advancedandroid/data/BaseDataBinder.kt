package com.nitrosoft.ua.advancedandroid.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

abstract class BaseDataBinder<Data, Request> : DataSourceBinder<Data, Request> {

    private var result: Flow<RepoState<Data>> = flow {

        emit(RepoState.Loading<Data>(null))

        loadFromDb().collect {
            emit(RepoState.Loading(it))
            if (shouldFetch(it)) {
                when (val fetchData = fetchData()) {
                    is DataResource.Success -> {
                        saveRequest(fetchData.data)
                    }
                    is DataResource.Error -> {
                        onFetchFailed()
                        emit(RepoState.Error(fetchData.error))
                    }
                }
            }
            loadFromDb().collect { newData ->
                emit(RepoState.Success(newData))
            }
        }
    }

    override fun getData(): Flow<RepoState<Data>> {
        return result
    }
}