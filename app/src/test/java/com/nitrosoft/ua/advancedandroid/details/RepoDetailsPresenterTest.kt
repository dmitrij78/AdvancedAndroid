package com.nitrosoft.ua.advancedandroid.details

import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.testutils.TestUtils
import com.squareup.moshi.Types
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException

class RepoDetailsPresenterTest {

    @Mock
    private lateinit var repoRepository: RepoRepository

    @Mock
    private lateinit var viewModel: RepoDetailsViewModel

    @Mock
    private lateinit var repoConsumer: Consumer<Repo>

    @Mock
    private lateinit var contributorsConsumer: Consumer<List<Contributor>>

    @Mock
    private lateinit var detailsErrorConsumer: Consumer<Throwable>

    @Mock
    private lateinit var contributorsErrorConsumer: Consumer<Throwable>

    private val repo: Repo = TestUtils.loadJson("mock/get_repo.json", Repo::class.java)
    private val contributors: List<Contributor> = TestUtils.loadJson(
            "mock/get_contributors.json",
            Types.newParameterizedType(List::class.java, Contributor::class.java))

    private val contributorsUrl = repo.contributorsUrl

    companion object {
        const val OWNER = "owner"
        const val NAME = "name"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(viewModel.processRepo()).thenReturn(repoConsumer)
        Mockito.`when`(viewModel.processContributors()).thenReturn(contributorsConsumer)
        Mockito.`when`(viewModel.detailsError()).thenReturn(detailsErrorConsumer)
        Mockito.`when`(viewModel.contributorsError()).thenReturn(contributorsErrorConsumer)

        Mockito.`when`(repoRepository.getRepo(OWNER, NAME)).thenReturn(Single.just(repo))
        Mockito.`when`(repoRepository.getContributors(contributorsUrl)).thenReturn(Single.just(contributors))
    }

    @Test
    fun repoDetails() {
        initPresenter()

        Mockito.verify(repoConsumer).accept(repo)
    }

    @Test
    fun repoDetailsError() {
        val t = IOException()
        Mockito.`when`(repoRepository.getRepo(OWNER, NAME)).thenReturn(Single.error(t))
        initPresenter()

        Mockito.verify(detailsErrorConsumer).accept(t)
    }

    @Test
    fun repoContributors() {
        initPresenter()

        Mockito.verify(contributorsConsumer).accept(contributors)
    }

    @Test
    fun repoContributorsError() {
        val t = IOException()
        Mockito.`when`(repoRepository.getContributors(contributorsUrl)).thenReturn(Single.error(t))
        initPresenter()

        Mockito.verify(contributorsErrorConsumer).accept(t)
        Mockito.verify(repoConsumer).accept(repo)
    }

    private fun initPresenter() {
        RepoDetailsPresenter(OWNER, NAME, repoRepository, viewModel)
    }
}