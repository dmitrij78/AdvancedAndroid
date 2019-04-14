package com.nitrosoft.ua.advancedandroid.trending

import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.data.TrendingReposResponse
import com.nitrosoft.ua.advancedandroid.testutils.TestUtils
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TrendingRepoViewModelTest {

    private lateinit var viewModel: TrendingRepoViewModel

    @Before
    fun setUp() {
        viewModel = TrendingRepoViewModel()
    }

    @Test
    fun loading() {
        val loadingObserver: TestObserver<Boolean> = viewModel.loading().test()
        viewModel.loadingUpdated().accept(true)
        viewModel.loadingUpdated().accept(false)

        loadingObserver.assertValues(true, false)
    }

    @Test
    fun repos() {
        val trendingReposResponse = TestUtils
                .loadJson<TrendingReposResponse>("mock/repos/search/get_trending_repos.json", TrendingReposResponse::class.java)
        viewModel.requestUpdated().accept(trendingReposResponse.repos)

        viewModel.repos().test().assertValue(trendingReposResponse.repos)
    }

    @Test
    fun error() {
        val errorObserver = viewModel.error().test()
        viewModel.onError().accept(IOException())
        viewModel.requestUpdated().accept(emptyList())

        errorObserver.assertValues(R.string.api_error_repos, -1)
    }
}