package com.nitrosoft.ua.poweradapter.item

import dagger.MapKey

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class RenderKey(val value: String)