package com.nitrosoft.ua.advancedandroid.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class BaseRepositoryDataSourceBinder<Data, ResponseModel> : DataSourceBinder<Data, ResponseModel> {

    private var result: Flow<RepoState<Data>> = flow {
        emit(RepoState.Loading(true))

        val storedData = loadFromDb().first()
        emit(RepoState.Success(storedData))

        if (shouldFetch(storedData)) {
            emit(RepoState.Syncing())
            when (val newData = fetchData()) {
                is DataWrapper.Success -> {
                    saveRequest(newData.data)
                }
                is DataWrapper.Error -> {
                    onFetchFailed()
                    emit(RepoState.Error(newData.throwable!!))
                }
            }
            emit(RepoState.Syncing())
        }

        emit(RepoState.Loading(false))
        loadFromDb().collect {
            emit(RepoState.Success(it))
        }
    }

    override fun getData(): Flow<RepoState<Data>> {
        return result
    }
}