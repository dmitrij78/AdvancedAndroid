package com.nitrosoft.ua.advancedandroid.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.nitrosoft.ua.advancedandroid.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

@HiltAndroidTest
class MainActivityTest {

    private var hiltRule = HiltAndroidRule(this)
    private val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @get:Rule
    var rule: TestRule = RuleChain.outerRule(hiltRule)
        .around(activityRule)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testFoo() {

        onView(withId(R.id.loadingIndicator)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }
}