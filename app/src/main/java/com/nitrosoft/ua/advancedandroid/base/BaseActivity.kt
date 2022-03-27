package com.nitrosoft.ua.advancedandroid.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.di.Injector
import com.nitrosoft.ua.advancedandroid.di.ScreenFragmentInjector
import com.nitrosoft.ua.advancedandroid.lifecycle.ActivityLifecycleTask
import com.nitrosoft.ua.advancedandroid.activity.ui.ActivityViewInterceptor
import com.nitrosoft.ua.advancedandroid.ui.FragmentProvider
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), FragmentProvider {

    companion object {
        const val ACTIVITY_INSTANCE_ID_KEY = "instance_id"
    }

    @Inject
    lateinit var screenInjector: ScreenFragmentInjector

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    @Inject
    lateinit var activityViewInterceptor: ActivityViewInterceptor

    @Inject
    lateinit var activityLifecycleTasks: Set<@JvmSuppressWildcards ActivityLifecycleTask>

    private lateinit var instanceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        instanceId = when (savedInstanceState) {
            null -> UUID.randomUUID().toString()
            else -> savedInstanceState.getString(ACTIVITY_INSTANCE_ID_KEY) ?: ""
        }
        Injector.inject(this)
        super.onCreate(savedInstanceState)

        activityViewInterceptor.setContentView(this, layoutRes())

        findViewById<View>(R.id.screenContainer)
            ?: throw NullPointerException("Activity must have a view with id: screen_container")

        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onCreate(this)
        }

    }

    override fun onStart() {
        super.onStart()
        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onStart(this)
        }
    }

    override fun onResume() {
        super.onResume()
        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onResume(this)
        }
    }

    override fun onPause() {
        super.onPause()
        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onPause(this)
        }
    }

    override fun onStop() {
        super.onStop()
        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onStop(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            Injector.clear(this)
        }
        activityViewInterceptor.clear()
        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onDestroy(this)
        }
    }

    override fun onBackPressed() {
        if (!screenNavigator.pop()) {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ACTIVITY_INSTANCE_ID_KEY, instanceId)
    }

    fun getInstanceId(): String {
        return instanceId
    }

    abstract fun layoutRes(): View

    abstract override fun initialFragment(): Fragment
}