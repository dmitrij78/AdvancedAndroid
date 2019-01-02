package com.nitrosoft.ua.advancedandroid.trending

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.TestApplication
import com.nitrosoft.ua.advancedandroid.data.TestRepoService
import com.nitrosoft.ua.advancedandroid.home.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class TrendingReposControllerTest {

    @Inject
    lateinit var repoService: TestRepoService

    @Suppress("RedundantVisibilityModifier")
    @Rule
    @JvmField
    public val activityTestRule: ActivityTestRule<MainActivity> =
            ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
        TestApplication.getComponent().inject(this)
    }

    @Test
    fun loadRepos() {
        repoService.sendError = false
        activityTestRule.launchActivity(null)

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
        repoService.sendError = true
        activityTestRule.launchActivity(null)

        onView(withId(R.id.loadingIndicator))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.repoList))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        onView(withId(R.id.errorText)).check(matches(allOf(withText(R.string.api_error_repos), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))))
    }
}