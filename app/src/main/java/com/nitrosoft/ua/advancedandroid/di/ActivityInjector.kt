package com.nitrosoft.ua.advancedandroid.di

import android.app.Activity
import android.content.Context
import com.nitrosoft.ua.advancedandroid.base.BaseActivity
import com.nitrosoft.ua.advancedandroid.base.MyApplication
import dagger.android.AndroidInjector
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ActivityInjector /*@Inject*/ constructor(
        private val activityInjectors: Map<KClass<out Activity>, Provider<AndroidInjector.Factory<out Activity>>>) {

    private val cache = HashMap<String, AndroidInjector<out Activity>>()

/*
    companion object {
        fun get(context: Context): ActivityInjector {
            return (context.applicationContext as MyApplication).activityInjector
        }
    }
*/

    fun inject(activity: Activity) {
        if (activity !is BaseActivity) {
            throw IllegalArgumentException("Activity must extend of BaseActivity")
        }

        val instanceId = activity.getInstanceId()
        if (cache.containsKey(instanceId)) {
            @Suppress("UNCHECKED_CAST")
            (cache[instanceId] as AndroidInjector<Activity>).inject(activity)
            return
        }

        @Suppress("UNCHECKED_CAST")
        val factory = activityInjectors[activity::class]?.get() as AndroidInjector.Factory<Activity>
        val injector = factory.create(activity)
        cache[instanceId] = injector
        injector.inject(activity)
    }

    fun clear(activity: Activity) {
        if (activity !is BaseActivity) {
            throw IllegalArgumentException("Activity must extend of BaseActivity")
        }

        cache.remove(activity.getInstanceId())
    }
}