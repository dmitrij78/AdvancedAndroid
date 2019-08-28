package com.nitrosoft.ua.advancedandroid.paging

import androidx.paging.PageKeyedDataSource
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.models.Repo
import javax.inject.Inject

class RepoPagingDataSource @Inject constructor(val repoRepository: RepoRepository) : PageKeyedDataSource<Long, Repo>() {

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Repo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Repo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Repo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}