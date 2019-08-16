package com.nitrosoft.ua.advancedandroid.trending

import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.data.TrendingReposResponse
import com.nitrosoft.ua.advancedandroid.lifecycle.DisposableManager
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.advancedandroid.testutils.TestUtils
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException

class TrendingReposPresenterTest {

    init {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Mock lateinit var repoRepository: RepoRepository
    @Mock lateinit var viewModel: TrendingRepoViewModel
    @Mock lateinit var recyclerDataSource: RecyclerDataSource
    @Mock lateinit var disposableManager: DisposableManager
    @Mock lateinit var screenNavigator: ScreenNavigator
    @Mock lateinit var onErrorConsumer: Consumer<Throwable>
    @Mock lateinit var loadingConsumer: Consumer<Boolean>

    private lateinit var presenter: TrendingReposPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(viewModel.loadingUpdated()).thenReturn(loadingConsumer)
        Mockito.`when`(viewModel.onError()).thenReturn(onErrorConsumer)

        Mockito.`when`(viewModel.reposUpdated()).thenReturn(Action { })
    }

    @Test
    fun onRepoClicked() {
        val repo = TestUtils.loadJson<Repo>("mock/repos/get_repo.json", Repo::class.java)

        setupSuccess()
        initializePresenter()

        presenter.onRepoClicked(repo)

        Mockito.verify(screenNavigator).goToRepoDetails(repo.user.login, repo.name)
    }

    @Test
    fun repoLoaded() {
        val repos = setupSuccess()

        initializePresenter()

        Mockito.verify(repoRepository).getTrendingRepos()
        Mockito.verify(recyclerDataSource).setData(repos.map { repo: Repo -> RepoListItem(repo) })
        Mockito.verifyZeroInteractions(onErrorConsumer)
    }

    @Test
    fun repoLoadedError() {
        val error = setupError()
        initializePresenter()

        Mockito.verify(onErrorConsumer).accept(error)
        Mockito.verifyZeroInteractions(recyclerDataSource)
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
                "mock/repos/search/get_trending_repos.json",
                TrendingReposResponse::class.java
        )
        val repos = response.repos

        Mockito.`when`(repoRepository.getTrendingRepos()).thenReturn(Single.just(repos))

        return repos
    }

    private fun setupError(): Throwable {
        val error = IOException()

        Mockito.`when`(repoRepository.getTrendingRepos()).thenReturn(Single.error(error))

        return error
    }

    private fun initializePresenter() {
        presenter = TrendingReposPresenter(
                disposableManager,
                viewModel,
                repoRepository,
                screenNavigator,
                recyclerDataSource)
    }
}