package com.nitrosoft.ua.advancedandroid.data

import io.reactivex.Single
import retrofit2.http.GET

interface RepoService {

    @GET("/search/repositories?q=language:java&order=desc&sort=stars")
    fun getTrendingRepos(): Single<TrendingReposResponse>
}