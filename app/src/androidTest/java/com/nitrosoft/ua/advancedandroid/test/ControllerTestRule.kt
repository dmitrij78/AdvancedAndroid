package com.nitrosoft.ua.advancedandroid.test

import android.app.Activity

import androidx.test.rule.ActivityTestRule

class ControllerTestRule<T : Activity>(activityClass: Class<T>) :
        ActivityTestRule<T>(activityClass, true, false)
