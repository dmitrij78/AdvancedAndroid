package com.nitrosoft.ua.advancedandroid.data

import kotlinx.coroutines.flow.Flow

interface DataSourceBinder<Data, Request> {

    suspend fun loadFromDb(): Flow<Data>

    suspend fun fetchData(): DataResource<Request>

    suspend fun saveRequest(request: Request?)

    fun shouldFetch(data: Data?): Boolean

    fun onFetchFailed()

    fun getData(): Flow<RepoState<Data>>
}