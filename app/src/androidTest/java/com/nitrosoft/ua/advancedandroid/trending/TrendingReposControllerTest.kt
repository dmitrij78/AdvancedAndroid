package com.nitrosoft.ua.advancedandroid.trending


import android.view.View
import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.data.TestRepoService
import com.nitrosoft.ua.advancedandroid.test.FragmentTest
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Test

class TrendingReposControllerTest : FragmentTest() {

    @Before
    fun setUp() {
        testRule.clearTest()
    }

    @Test
    fun loadRepos() {
        launch()

        onView(withId(R.id.loadingIndicator)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.errorText)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.repoList)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(allOf<View>(withId(R.id.repoName), withText("RxJava"))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun loadReposError() {
        repoService.setErrorFlags(TestRepoService.FLAG_TRENDING_REPOS)
        launch()

        onView(withId(R.id.loadingIndicator)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.repoList)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.errorText)).check(matches(allOf<View>(withText(R.string.api_error_repos), withEffectiveVisibility(Visibility.VISIBLE))))
    }

    override fun controllerToLaunch(): Fragment {
        return TrendingReposFragment()
    }
}
