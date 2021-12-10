package com.nitrosoft.ua.advancedandroid.details

import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.testutils.TestUtils
import com.squareup.moshi.Types
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*

class RepoDetailsViewModelTest {

    private val repo: Repo = initRepo()
    private val contributors: List<Contributor> = initContributors()

    private lateinit var viewModel: RepoDetailsViewModel

    init {
        Locale.setDefault(Locale("ru", "RU"))
    }

    @Before
    fun setUp() {
        viewModel = RepoDetailsViewModel()
    }

    //TODO  Исправить тест
//    @Test
//    fun details() {
//        viewModel.processRepo().accept(repo)
//
//        viewModel.details().test().assertValue(
//                RepoDetailsState(
//                        loading = false,
//                        name = "RxJava",
//                        description = "RxJava Reactive Extensions for the JVM",
//                        createDate = "янв 08, 2013",
//                        updateDate = "окт 06, 2017"
//                )
//        )
//    }

    @Test
    fun contributors() {
        viewModel.processContributors().accept(contributors)

        viewModel.contributors().test().assertValue(
            ContributorState(
                loading = false
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

    private fun initContributors(): List<Contributor> {
        return TestUtils.loadJson("mock/repos/contributors/get_contributors.json",
                Types.newParameterizedType(List::class.java, Contributor::class.java))
    }

    private fun initRepo(): Repo {
        return TestUtils.loadJson("mock/repos/get_repo.json", Repo::class.java)
    }
}