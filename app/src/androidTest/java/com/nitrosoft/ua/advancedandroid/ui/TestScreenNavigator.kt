package com.nitrosoft.ua.advancedandroid.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nitrosoft.ua.advancedandroid.lifecycle.ActivityLifecycleTask
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestScreenNavigator @Inject constructor() : ActivityLifecycleTask(), ScreenNavigator {

    private var defaultScreenNavigator: DefaultScreenNavigator = DefaultScreenNavigator()
    private var overrideFragment: Fragment? = null

    override fun onCreate(appCompatActivity: AppCompatActivity) {
        require(appCompatActivity is FragmentProvider) { "Activity must be instance of FragmentProvider" }

        val routerProvider = appCompatActivity as FragmentProvider
        val launchFragment: Fragment = overrideFragment ?: routerProvider.initialFragment()
        defaultScreenNavigator.init(appCompatActivity.supportFragmentManager, launchFragment)
    }

    override fun onDestroy(appCompatActivity: AppCompatActivity) {
        defaultScreenNavigator.onDestroy(appCompatActivity)
    }

    override fun pop(): Boolean {
        return defaultScreenNavigator.pop()
    }

    override fun goToRepoDetails(repoOwner: String, repoName: String) {
        defaultScreenNavigator.goToRepoDetails(repoOwner, repoName)
    }

    fun overrideInitialFragment(fragment: Fragment) {
        this.overrideFragment = fragment
    }
}