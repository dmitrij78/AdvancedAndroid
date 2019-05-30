package com.nitrosoft.ua.advancedandroid.di;

import com.nitrosoft.ua.advancedandroid.lifecycle.DisposableManager;

import dagger.android.AndroidInjector;

public interface ScreenComponent<T> extends AndroidInjector<T> {

    @ForScreen
    DisposableManager diposableManager();
}
