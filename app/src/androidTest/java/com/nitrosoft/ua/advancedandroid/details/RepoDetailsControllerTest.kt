package com.nitrosoft.ua.advancedandroid.details

import androidx.fragment.app.Fragment
import androidx.test.espresso.matcher.ViewMatchers
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.data.TestRepoService
import com.nitrosoft.ua.advancedandroid.test.FragmentTest
import org.junit.Before
import org.junit.Test

class RepoDetailsControllerTest : FragmentTest() {

    @Before
    fun setUp() {
        testRule.clearTest()
    }

    @Test
    fun repoDetailsSuccess() {
        launch()
        RepoDetailsRobot.init()
                .verifyLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyName("RxJava")
                .verifyDescription("RxJava Reactive Extensions for the JVM")
                .verifyCreatedDate("Jan 08, 2013")
                .verifyUpdatedDate("Oct 06, 2017")
    }

    @Test
    fun repoDetailsError() {
        repoService.setErrorFlags(TestRepoService.FLAG_GET_REPO)
        launch()
        RepoDetailsRobot.init()
                .verifyLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyContentVisibility(ViewMatchers.Visibility.GONE)
                .verifyErrorText(R.string.api_error_single_repo)
    }

    @Test
    fun contributorsSuccess() {
        launch()
        @Suppress("SpellCheckingInspection")
        RepoDetailsRobot.init()
                .verifyContributorsLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyContributorsErrorVisibility(ViewMatchers.Visibility.GONE)
                .verifyContributorShown("benjchristensen")
    }

    @Test
    fun repoSuccessContributorsError() {
        repoService.setErrorFlags(TestRepoService.FLAG_GET_CONTRIBUTORS)
        launch()
        RepoDetailsRobot.init()
                .verifyLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyContributorsLoadingVisibility(ViewMatchers.Visibility.GONE)
                .verifyContributorsErrorText(R.string.api_error_contributors)
                .verifyErrorVisibility(ViewMatchers.Visibility.GONE)
    }

    @Test
    fun loadingRepo() {
        repoService.setHoldFlags(TestRepoService.FLAG_GET_REPO)
        launch()
        RepoDetailsRobot.init().verifyLoadingVisibility(ViewMatchers.Visibility.VISIBLE)
    }

    @Test
    fun loadingContributors() {
        repoService.setHoldFlags(TestRepoService.FLAG_GET_CONTRIBUTORS)
        launch()
        RepoDetailsRobot.init().verifyContributorsLoadingVisibility(ViewMatchers.Visibility.VISIBLE)
    }

    override fun controllerToLaunch(): Fragment {
        return RepoDetailsFragment.newInstance("ReactiveX", "RxJava")
    }
}