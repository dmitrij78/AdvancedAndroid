package com.nitrosoft.ua.advancedandroid.trending

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nitrosoft.ua.advancedandroid.data.TrendingReposResponse
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.advancedandroid.repository.RepoRepository
import com.nitrosoft.ua.advancedandroid.testutils.TestUtils
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import io.reactivex.Single
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.IOException

class TrendingRepoViewModelTest {

    @Mock lateinit var repoRepository: RepoRepository
    @Mock lateinit var screenNavigator: ScreenNavigator
    @Mock lateinit var testObserver: Observer<RepoState<List<RepoListItem>>>

    @Captor lateinit var dataCaptor: ArgumentCaptor<RepoState<List<RepoListItem>>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun onRepoListUpdateSuccess() {
        setupSuccess()

        initViewModel().onRepoListUpdate().observeForever(testObserver)

        verify(testObserver, atMost(2)).onChanged(dataCaptor.capture())
        assertThat(dataCaptor.allValues, hasItem(instanceOf(RepoState.Success::class.java)))
    }

    @Test
    fun onRepoListUpdateError() {
        setupError()
        initViewModel().onRepoListUpdate().observeForever(testObserver)

        verify(testObserver, atMost(2)).onChanged(dataCaptor.capture())
        assertThat(dataCaptor.allValues, hasItem(instanceOf(RepoState.Error::class.java)))
    }

    @Test
    fun onRepoClicked() {
        val repo = loadRepo()
        initViewModel().onRepoClicked(repo)

        verify(screenNavigator).goToRepoDetails(repo.user.login, repo.name)
    }

    private fun loadRepo(): Repo {
        return TestUtils.loadJson<Repo>("mock/repos/get_repo.json", Repo::class.java)
    }

    private fun setupSuccess(): List<Repo> {
        val response = TestUtils.loadJson<TrendingReposResponse>(
                "mock/repos/search/get_trending_repos.json",
                TrendingReposResponse::class.java
        )
        val repos = response.repos
        `when`(repoRepository.getTrendingRepos()).thenReturn(Single.just(repos))

        return repos
    }

    private fun setupError(): Throwable {
        val error = IOException()
        `when`(repoRepository.getTrendingRepos()).thenReturn(Single.error(error))
        return error
    }

    private fun initViewModel(): TrendingRepoViewModel {
        return TrendingRepoViewModel(repoRepository, screenNavigator)
    }
}