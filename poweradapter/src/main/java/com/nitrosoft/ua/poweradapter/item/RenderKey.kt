package com.nitrosoft.ua.poweradapter.item

import dagger.MapKey


@MapKey
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RenderKey(val value: String)