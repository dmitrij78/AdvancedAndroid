package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.testutils.TestUtils
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.MockitoAnnotations
import javax.inject.Provider

class RepoRepositoryTest {

    @Mock
    lateinit var repoRequesterProvider: Provider<RepoRequester>

    @Mock
    lateinit var repoRequester: RepoRequester

    private lateinit var repository: RepoRepository
    private lateinit var response: TrendingReposResponse

    private lateinit var rxJavaRepo: Repo
    private lateinit var otherRepo: Repo

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(repoRequesterProvider.get()).thenReturn(repoRequester)

        response = TestUtils.loadJson(
                "mock/repos/search/get_trending_repos.json",
                TrendingReposResponse::class.java
        )

        `when`(repoRequester.getTrendingRepos()).thenReturn(Single.just(response.repos))
        rxJavaRepo = response.repos[0]
        otherRepo = response.repos[1]

        repository = RepoRepository(repoRequesterProvider, Schedulers.trampoline())
    }

    @Test
    fun getTrendingRepos() {
        repository.getTrendingRepos().test().assertValue(response.repos)

        val modified: MutableList<Repo> = response.repos.toMutableList()
        modified.removeAt(0)
        `when`(repoRequester.getTrendingRepos()).thenReturn(Single.just(modified))

        repository.getTrendingRepos().test().assertValue(response.repos)
    }

    @Test
    fun getRepo() {
        repository.getTrendingRepos().subscribe()

        `when`(repoRequester.getRepo(anyString(), anyString())).thenReturn(Single.just(otherRepo))

        repository.getRepo("ReactiveX", "RxJava").test().assertValue(rxJavaRepo)
        repository.getRepo("NoInCache", "NoInCache").test().assertValue(otherRepo)
    }


}