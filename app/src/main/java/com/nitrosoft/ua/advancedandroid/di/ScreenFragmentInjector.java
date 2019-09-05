package com.nitrosoft.ua.advancedandroid.di;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.nitrosoft.ua.advancedandroid.base.BaseActivity;
import com.nitrosoft.ua.advancedandroid.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.AndroidInjector;

@ActivityScope
public class ScreenFragmentInjector {

    private final Map<Class<?>, Provider<AndroidInjector.Factory<?>>> fragmentInjectors;
    private final Map<String, AndroidInjector<?>> cache = new HashMap<>();

    @Inject
    ScreenFragmentInjector(Map<Class<?>, Provider<AndroidInjector.Factory<?>>> injectors) {
        this.fragmentInjectors = injectors;
    }

    static ScreenFragmentInjector get(Activity activity) {
        if (!(activity instanceof BaseActivity)) {
            throw new IllegalArgumentException("Activity mus extend BaseActivity");
        }

        return ((BaseActivity) activity).getScreenInjector();
    }

    void inject(Fragment fragment) {
        if (!(fragment instanceof BaseFragment)) {
            throw new IllegalArgumentException("Fragment must extends BaseFragment");
        }


        String instanceId = getInstanceId(fragment);
        if (cache.containsKey(instanceId)) {
            //noinspection unchecked,ConstantConditions
            ((AndroidInjector<Fragment>) cache.get(instanceId)).inject(fragment);
            return;
        }

        //noinspection unchecked,ConstantConditions
        AndroidInjector.Factory<Fragment> injectorFactory =
                (AndroidInjector.Factory<Fragment>) fragmentInjectors.get(fragment.getClass()).get();
        AndroidInjector<Fragment> injector = injectorFactory.create(fragment);
        cache.put(instanceId, injector);
        injector.inject(fragment);
    }

    void clear(Fragment fragment) {
        AndroidInjector<?> injector = cache.remove(getInstanceId(fragment));
        if (injector instanceof ScreenComponent) {
            ((ScreenComponent) injector).diposableManager().disposable();
        }
    }

    private String getInstanceId(Fragment fragment) {
        Bundle arguments = fragment.getArguments();
        if (arguments == null || !arguments.containsKey("instance_id")) {
            throw new IllegalArgumentException("Fragment must contains args and has instance_id string in it");
        }

        return fragment.getArguments().getString("instance_id");
    }
}
