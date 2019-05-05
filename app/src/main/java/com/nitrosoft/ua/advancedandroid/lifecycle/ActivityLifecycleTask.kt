package com.nitrosoft.ua.advancedandroid.lifecycle

import androidx.appcompat.app.AppCompatActivity

abstract class ActivityLifecycleTask {

    open fun onCreate(appCompatActivity: AppCompatActivity) {}

    open fun onStart(appCompatActivity: AppCompatActivity) {}

    open fun onResume(appCompatActivity: AppCompatActivity) {}

    open fun onPause(appCompatActivity: AppCompatActivity) {}

    open fun onStop(appCompatActivity: AppCompatActivity) {}

    open fun onDestroy(appCompatActivity: AppCompatActivity) {}
}