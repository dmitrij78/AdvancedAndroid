package com.nitrosoft.ua.advancedandroid.details

import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.testutils.TestUtils
import com.squareup.moshi.Types
import org.junit.Before
import org.junit.Test
import java.io.IOException

class RepoDetailsViewModelTest {

    private val repo: Repo = TestUtils.loadJson("mock/get_repo.json", Repo::class.java)
    private val contributors: List<Contributor> = TestUtils.loadJson("mock/get_contributors.json",
            Types.newParameterizedType(List::class.java, Contributor::class.java))
    private lateinit var viewModel: RepoDetailsViewModel

    @Before
    fun setUp() {
        viewModel = RepoDetailsViewModel()
    }

    @Test
    fun details() {
        viewModel.processRepo().accept(repo)

        viewModel.details().test().assertValue(
                RepoDetailsState(
                        loading = false,
                        name = "RxJava",
                        description = "RxJava Reactive Extensions for the JVM",
                        createDate = "янв 08, 2013",
                        updateDate = "окт 06, 2017"
                )
        )
    }

    @Test
    fun contributors() {
        viewModel.processContibutors().accept(contributors)

        viewModel.contributors().test().assertValue(
                ContributorState(
                        loading = false,
                        contributors = contributors
                )
        )
    }

    @Test
    fun detailsError() {
        viewModel.detailsError().accept(IOException())

        viewModel.details().test().assertValue(
                RepoDetailsState(
                        loading = false,
                        errorRes = R.string.api_error_single_repo)
        )
    }

    @Test
    fun contributorsError() {
        viewModel.contributorsError().accept(IOException())

        viewModel.contributors().test().assertValue(
                ContributorState(
                        loading = false,
                        errorRes = R.string.api_error_contributors
                )
        )
    }
}