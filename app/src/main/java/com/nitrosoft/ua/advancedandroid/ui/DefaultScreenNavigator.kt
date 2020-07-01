package com.nitrosoft.ua.advancedandroid.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.TAG
import com.nitrosoft.ua.advancedandroid.details.RepoDetailsFragment
import com.nitrosoft.ua.advancedandroid.lifecycle.ActivityLifecycleTask
import timber.log.Timber
import javax.inject.Inject

class DefaultScreenNavigator @Inject constructor() :
        ActivityLifecycleTask(), ScreenNavigator {

    private var fragmentManager: FragmentManager? = null

    override fun onCreate(appCompatActivity: AppCompatActivity) {
        require(appCompatActivity is FragmentProvider) { "Activity must be instance of FragmentProvider" }

        Timber.tag(TAG).d("DefaultScreenNavigator.onCreate")

        /*val fragmentManager = appCompatActivity.supportFragmentManager
        val fragmentProvider = appCompatActivity as FragmentProvider
        init(fragmentManager, fragmentProvider.initialFragment())*/
    }

    override fun onDestroy(appCompatActivity: AppCompatActivity) {
        fragmentManager = null
    }

    fun init(fragmentManager: FragmentManager, initialFragment: Fragment) {
        this.fragmentManager = fragmentManager
        if (fragmentManager.fragments.isEmpty()) {
            fragmentManager.beginTransaction()
                    .add(R.id.screenContainer, initialFragment)
                    .commit()
        }
    }

    override fun pop(): Boolean {
        return fragmentManager != null && fragmentManager!!.popBackStackImmediate()
    }

    override fun goToRepoDetails(repoOwner: String, repoName: String) {
        fragmentManager?.beginTransaction()
                ?.replace(R.id.screenContainer, RepoDetailsFragment.newInstance(repoName, repoOwner))
                ?.addToBackStack(null)
                ?.commit()
    }
}