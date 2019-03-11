package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface RepoService {

    @GET("/search/repositories?q=language:java&order=desc&sort=stars")
    fun getTrendingRepos(): Single<TrendingReposResponse>

    @GET("/repos/{owner}/{name}")
    fun getRepo(@Path("owner") repoOwner: String, @Path("name") repoName: String): Single<Repo>

    @GET("/repos/{owner}/{name}")
    fun getContributors(@Url url: String): Single<List<Contributor>>
}