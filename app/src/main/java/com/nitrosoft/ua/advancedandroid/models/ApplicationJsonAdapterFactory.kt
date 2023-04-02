package com.nitrosoft.ua.advancedandroid.models

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

//@KotshiJsonAdapterFactory
//abstract class ApplicationJsonAdapterFactory : JsonAdapter.Factory {
//    companion object {
//
//        @JvmField
//        val INSTANCE: ApplicationJsonAdapterFactory = KotshiApplicationJsonAdapterFactory
//    }
//}

@KotshiJsonAdapterFactory
object ApplicationJsonAdapterFactory : JsonAdapter.Factory by KotshiApplicationJsonAdapterFactory