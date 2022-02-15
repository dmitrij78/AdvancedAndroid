package com.nitrosoft.ua.advancedandroid.ui

import android.app.Activity
import android.view.View

interface ActivityViewInterceptor {

    fun setContentView(activity: Activity, activityRoot: View)

    fun clear()

    companion object {

        val DEFAULT: ActivityViewInterceptor = object : ActivityViewInterceptor {
            override fun setContentView(activity: Activity, activityRoot: View) {
                activity.setContentView(activityRoot)
            }

            override fun clear() {}
        }
    }
}
