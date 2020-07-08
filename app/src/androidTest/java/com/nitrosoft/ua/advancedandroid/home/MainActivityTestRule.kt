package com.nitrosoft.ua.advancedandroid.home

import android.app.Activity
import androidx.test.rule.ActivityTestRule

class MainActivityTestRule<T : Activity>(activityClass: Class<T>) :
    ActivityTestRule<T>(activityClass, true, false) {

    fun clearTest() {
    }
}