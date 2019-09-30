package com.nitrosoft.ua.advancedandroid.trending

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.data.Resource
import com.nitrosoft.ua.advancedandroid.data.TrendingReposResponse
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.advancedandroid.testutils.TestUtils
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import io.reactivex.Single
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.io.IOException

class TrendingRepoViewModelTest {

    @Mock lateinit var repoRepository: RepoRepository
    @Mock lateinit var screenNavigator: ScreenNavigator
    @Mock lateinit var testObserver: Observer<Resource<List<RepoListItem>>>

    @Captor lateinit var successCaptor: ArgumentCaptor<Resource<List<RepoListItem>>>

    private var holdFlags: Int = 0

    @get:Rule
    val rule = InstantTaskExecutorRule()

    companion object {
        const val DELAY_MILLIS = 10000L
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
        holdFlags = 0
    }

    @Test
    fun onRepoListUpdate() {
        holdFlags = 1
        setupSuccess()

        initViewModel().onRepoListUpdate().observeForever(testObserver)

        verify(testObserver).onChanged(successCaptor.capture())
        assertThat(successCaptor.value, instanceOf(Resource.Loading::class.java))
    }

    @Test
    fun onRepoClicked() {
    }

    private fun setupSuccess(): List<Repo> {
        val response = TestUtils.loadJson<TrendingReposResponse>(
                "mock/repos/search/get_trending_repos.json",
                TrendingReposResponse::class.java
        )
        val repos = response.repos
        if (holdFlags != 0) {
            `when`(repoRepository.getTrendingRepos()).thenReturn(Single.create { emitter ->
                Thread(Runnable {
                    while (holdFlags != 0) {
                        Thread.sleep(50)
                    }
                    emitter.onSuccess(repos)
                }).start()
            })
        } else {
            `when`(repoRepository.getTrendingRepos()).thenReturn(Single.just(repos))
        }


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