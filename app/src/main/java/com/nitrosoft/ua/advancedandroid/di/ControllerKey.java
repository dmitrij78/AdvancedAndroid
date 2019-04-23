package com.nitrosoft.ua.advancedandroid.di;

import com.bluelinelabs.conductor.Controller;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

@MapKey
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerKey {
    Class<? extends Controller> value();
}
