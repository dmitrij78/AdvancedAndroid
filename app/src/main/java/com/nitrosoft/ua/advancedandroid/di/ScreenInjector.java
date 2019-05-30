package com.nitrosoft.ua.advancedandroid.di;

import android.app.Activity;

import com.bluelinelabs.conductor.Controller;
import com.nitrosoft.ua.advancedandroid.base.BaseActivity;
import com.nitrosoft.ua.advancedandroid.base.BaseController;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.AndroidInjector;

@ActivityScope
public class ScreenInjector {

    private final Map<Class<?>, Provider<AndroidInjector.Factory<?>>> ControllerInjectors;
    private final Map<String, AndroidInjector<?>> cache = new HashMap<>();

    @Inject
    ScreenInjector(Map<Class<?>, Provider<AndroidInjector.Factory<?>>> ControllerInjectors) {
        this.ControllerInjectors = ControllerInjectors;
    }

    void inject(Controller controller) {
        if (!(controller instanceof BaseController)) {
            throw new IllegalArgumentException("Controller must extends BaseController");
        }

        String instanceId = controller.getInstanceId();
        if (cache.containsKey(instanceId)) {
            //noinspection unchecked
            ((AndroidInjector<Controller>) cache.get(instanceId)).inject(controller);
            return;
        }

        //noinspection unchecked
        AndroidInjector.Factory<Controller> injectorFactory =
                (AndroidInjector.Factory<Controller>) ControllerInjectors.get(controller.getClass()).get();
        AndroidInjector<Controller> injector = injectorFactory.create(controller);
        cache.put(instanceId, injector);
        injector.inject(controller);
    }

    void clear(Controller controller) {
        AndroidInjector<?> injector = cache.remove(controller.getInstanceId());
        if (injector instanceof ScreenComponent) {
            ((ScreenComponent) injector).diposableManager().disposable();
        }
    }

    static ScreenInjector get(Activity activity) {
        if (!(activity instanceof BaseActivity)) {
            throw new IllegalArgumentException("Activity mus extend BaseActivity");
        }

        return ((BaseActivity) activity).getScreenInjector();
    }
}
