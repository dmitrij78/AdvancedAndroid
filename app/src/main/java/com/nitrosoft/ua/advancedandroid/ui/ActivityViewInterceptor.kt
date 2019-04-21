package com.nitrosoft.ua.advancedandroid.ui

import android.app.Activity

interface ActivityViewInterceptor {

    fun setContentView(activity: Activity, layoutRes: Int)

    fun clear()

    companion object {

        val DEFAULT: ActivityViewInterceptor = object : ActivityViewInterceptor {
            override fun setContentView(activity: Activity, layoutRes: Int) {
                activity.setContentView(layoutRes)
            }

            override fun clear() {}
        }
    }
}
