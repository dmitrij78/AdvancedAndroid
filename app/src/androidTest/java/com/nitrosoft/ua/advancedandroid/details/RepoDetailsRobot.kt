package com.nitrosoft.ua.advancedandroid.details

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.nitrosoft.ua.advancedandroid.R
import org.hamcrest.Matchers.allOf

internal class RepoDetailsRobot private constructor() {

    fun verifyName(name: String): RepoDetailsRobot {
        onView(withId(R.id.tv_repo_name)).check(matches(withText(name)))
        return this
    }

    fun verifyDescription(description: String): RepoDetailsRobot {
        onView(withId(R.id.tv_repo_description)).check(matches(withText(description)))
        return this
    }

    fun verifyCreatedDate(createdDate: String): RepoDetailsRobot {
        onView(withId(R.id.tv_creation_date)).check(matches(withText(createdDate)))
        return this
    }

    fun verifyUpdatedDate(updatedDate: String): RepoDetailsRobot {
        onView(withId(R.id.tv_updated_date)).check(matches(withText(updatedDate)))
        return this
    }

    fun verifyErrorText(textRes: Int?): RepoDetailsRobot {
        if (textRes == null) {
            onView(withId(R.id.tv_error)).check(matches(withText("")))
        } else {
            onView(withId(R.id.tv_error)).check(matches(withText(textRes)))
        }
        return this
    }

    fun verifyErrorVisibility(visibility: ViewMatchers.Visibility): RepoDetailsRobot {
        onView(withId(R.id.tv_error)).check(matches(withEffectiveVisibility(visibility)))
        return this
    }

    fun verifyContributorsErrorText(textRes: Int?): RepoDetailsRobot {
        if (textRes == null) {
            onView(withText(R.id.tv_contributors_error)).check(matches(withText("")))
        } else {
            onView(withId(R.id.tv_contributors_error)).check(matches(withText(textRes)))
        }
        return this
    }

    fun verifyContributorsErrorVisibility(visibility: ViewMatchers.Visibility): RepoDetailsRobot {
        onView(withId(R.id.tv_contributors_error)).check(matches(withEffectiveVisibility(visibility)))
        return this
    }

    fun verifyLoadingVisibility(visibility: ViewMatchers.Visibility): RepoDetailsRobot {
        onView(withId(R.id.loading_indicator)).check(matches(withEffectiveVisibility(visibility)))
        return this
    }

    fun verifyContributorsLoadingVisibility(visibility: ViewMatchers.Visibility): RepoDetailsRobot {
        onView(withId(R.id.contributor_loading_indicator)).check(matches(withEffectiveVisibility(visibility)))
        return this
    }

    fun verifyContributorShown(login: String): RepoDetailsRobot {
        onView(allOf<View>(withId(R.id.userNameTv), withText(login))).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        return this
    }

    fun verifyContentVisibility(visibility: ViewMatchers.Visibility): RepoDetailsRobot {
        onView(withId(R.id.content)).check(matches(withEffectiveVisibility(visibility)))
        return this
    }

    companion object {

        fun init(): RepoDetailsRobot {
            return RepoDetailsRobot()
        }
    }
}
