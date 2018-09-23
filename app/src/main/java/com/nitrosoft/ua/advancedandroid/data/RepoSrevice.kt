package com.nitrosoft.ua.advancedandroid.data

import io.reactivex.Single
import retrofit2.http.GET

interface RepoSrevice {

    @GET("/search/repositories?q=language:java&order=desc&sort=stars")
    fun getTraendingRepos(): Single<TrendingReposResponse>
}