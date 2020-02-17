package com.nitrosoft.ua.advancedandroid.data

import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RateLimiter @Inject constructor() : BaseRateLimiter<String>(TIME_OUT, TimeUnit.SECONDS) {

    companion object {
        const val TIME_OUT = 10
    }
}