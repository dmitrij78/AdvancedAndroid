package com.nitrosoft.ua.advancedandroid.trending

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.data.TestRepoService
import com.nitrosoft.ua.advancedandroid.test.ControllerTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrendingReposControllerTest : ControllerTest() {

    @Test
    fun loadRepos() {
        repoService.clearErrorFlags()
        launch()

        onView(withId(R.id.loadingIndicator))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.errorText))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.repoList))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        onView(allOf(withId(R.id.repoName), withText("RxJava")))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun loadReposError() {
        repoService.errorFlags = TestRepoService.FLAG_TRENDING_REPO
        launch()

        onView(withId(R.id.loadingIndicator))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.repoList))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        onView(withId(R.id.errorText)).check(matches(allOf(withText(R.string.api_error_repos), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))))
    }

    override fun controllerToLaunch(): Controller {
        return TrendingReposController()
    }
}