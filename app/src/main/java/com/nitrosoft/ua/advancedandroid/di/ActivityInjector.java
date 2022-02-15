package com.nitrosoft.ua.advancedandroid.di;

import android.app.Activity;
import android.content.Context;

import com.nitrosoft.ua.advancedandroid.App;
import com.nitrosoft.ua.advancedandroid.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.android.AndroidInjector;

@SuppressWarnings("ConstantConditions")
@Singleton
public class ActivityInjector {

    private final Map<Class<?>, Provider<AndroidInjector.Factory<?>>> activityInjectors;
    private final Map<String, AndroidInjector<?>> cache = new HashMap<>();

    @Inject
    ActivityInjector(Map<Class<?>, Provider<AndroidInjector.Factory<?>>> activityInjectors) {
        this.activityInjectors = activityInjectors;
    }

    void inject(Activity activity) {
        if (!(activity instanceof BaseActivity)) {
            throw new IllegalArgumentException("Activity must extends BaseActivity");
        }

        String instanceId = ((BaseActivity) activity).getInstanceId();
        if (cache.containsKey(instanceId)) {
            //noinspection unchecked
            ((AndroidInjector<Activity>) cache.get(instanceId)).inject(activity);
            return;
        }

        //noinspection unchecked
        AndroidInjector.Factory<Activity> injectorFactory =
                (AndroidInjector.Factory<Activity>) Objects.requireNonNull(activityInjectors.get(activity.getClass())).get();
        AndroidInjector<Activity> injector = injectorFactory.create(activity);
        cache.put(instanceId, injector);
        injector.inject(activity);
    }

    void clear(Activity activity) {
        if (!(activity instanceof BaseActivity)) {
            throw new IllegalArgumentException("Activity must extends BaseActivity");
        }
        cache.remove(((BaseActivity) activity).getInstanceId());
    }

    static ActivityInjector get(Context context) {
        return ((App) context.getApplicationContext()).getActivityInjector();
    }
}
