package com.nitrosoft.ua.advancedandroid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nitrosoft.ua.advancedandroid.lifecycle.ActivityLifecycleTask
import com.nitrosoft.ua.advancedandroid.ui.ActivityViewInterceptor
import com.nitrosoft.ua.advancedandroid.ui.FragmentProvider
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), /*HasAndroidInjector,*/ FragmentProvider {

    companion object {
        const val ACTIVITY_INSTANCE_ID_KEY = "instance_id"
    }

    @Inject lateinit var activityViewInterceptor: ActivityViewInterceptor
    @Inject lateinit var activityLifecycleTasks: Set<@JvmSuppressWildcards ActivityLifecycleTask>
    @Inject lateinit var screenNavigator: ScreenNavigator

/*    @Inject lateinit var screenInjector: DispatchingAndroidInjector<Any>
    @Inject lateinit var screenNavigator: ScreenNavigator

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var activityLifecycleTasks: Set<@JvmSuppressWildcards ActivityLifecycleTask>*/

    override fun onCreate(savedInstanceState: Bundle?) {
        //Injector.inject(this)
        super.onCreate(savedInstanceState)

        activityViewInterceptor.setContentView(this, layoutRes())

        /*findViewById<View>(R.id.screenContainer)
                ?: throw NullPointerException("Activity must have a view with id: screen_container")*/

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

    abstract fun layoutRes(): Int

    abstract override fun initialFragment(): Fragment

    /*@Suppress("unused")
    protected inline fun <reified T : ViewModel> createViewModel(): T {
        return ViewModelProvider(this, viewModelFactory)[T::class.java]
    }*/

/*    override fun androidInjector(): AndroidInjector<Any> {
        return screenInjector
    }*/
}
