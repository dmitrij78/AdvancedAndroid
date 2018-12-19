package com.nitrosoft.ua.advancedandroid.trending

import com.nitrosoft.ua.advancedandroid.data.RepoRequester
import com.nitrosoft.ua.advancedandroid.data.TrendingReposResponse
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.testutils.TestUtils
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException

class TrendingReposPresenterTest {

    @Mock
    lateinit var repoRequester: RepoRequester

    @Mock
    lateinit var viewModel: TrendingRepoViewModel

    @Mock
    lateinit var onErrorConsumer: Consumer<Throwable>

    @Mock
    lateinit var onSuccessConsumer: Consumer<List<Repo>>

    @Mock
    lateinit var loadingConsumer: Consumer<Boolean>

    private lateinit var presenter: TrendingReposPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(viewModel.loadingUpdated()).thenReturn(loadingConsumer)
        Mockito.`when`(viewModel.onError()).thenReturn(onErrorConsumer)
        Mockito.`when`(viewModel.requestUpdated()).thenReturn(onSuccessConsumer)
    }

    @Test
    fun onRepoClicked() {
    }

    @Test
    fun repoLoaded() {
        val repos = setupSuccess()
        initializePresenter()

        Mockito.verify(repoRequester).getTrendingRepos()
        Mockito.verify(onSuccessConsumer).accept(repos)
        Mockito.verifyZeroInteractions(onErrorConsumer)

    }

    @Test
    fun repoLoadedError() {
        val error = setupError()
        initializePresenter()

        Mockito.verify(onErrorConsumer).accept(error)
        Mockito.verifyZeroInteractions(onSuccessConsumer)
    }

    @Test
    fun loadingSuccess() {
        setupSuccess()
        initializePresenter()

        val inOrder = Mockito.inOrder(loadingConsumer)
        inOrder.verify(loadingConsumer).accept(true)
        inOrder.verify(loadingConsumer).accept(false)
    }

    @Test
    fun loadingError() {
        setupError()
        initializePresenter()

        val inOrder = Mockito.inOrder(loadingConsumer)
        inOrder.verify(loadingConsumer).accept(true)
        inOrder.verify(loadingConsumer).accept(false)
    }

    private fun setupSuccess(): List<Repo> {
        val response = TestUtils.loadJson<TrendingReposResponse>(
                "mock/get_trending_repo.json",
                TrendingReposResponse::class.java
        )
        val repos = response.repos

        Mockito.`when`(repoRequester.getTrendingRepos()).thenReturn(Single.just(repos))

        return repos
    }

    private fun setupError(): Throwable {
        val error = IOException()

        Mockito.`when`(repoRequester.getTrendingRepos()).thenReturn(Single.error(error))

        return error
    }

    private fun initializePresenter() {
        presenter = TrendingReposPresenter(viewModel, repoRequester)
    }
}