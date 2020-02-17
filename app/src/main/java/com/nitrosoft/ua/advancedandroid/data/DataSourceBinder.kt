package com.nitrosoft.ua.advancedandroid.data

import kotlinx.coroutines.flow.Flow

interface DataSourceBinder<Data, Response> {

    suspend fun loadFromDb(): Flow<Data>

    suspend fun fetchData(): DataWrapper<Response>

    suspend fun saveRequest(response: Response?)

    fun shouldFetch(data: Data?): Boolean

    fun onFetchFailed()

    fun getData(): Flow<RepoState<Data>>
}