package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoReuqester @Inject constructor(val repoSrevice: RepoSrevice) {

    fun getTrendingRepos(): Single<List<Repo>> {
        return repoSrevice.getTraendingRepos()
                .map(TrendingReposResponse::repos)
                .subscribeOn(Schedulers.io())
    }
}