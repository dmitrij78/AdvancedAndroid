package com.nitrosoft.ua.advancedandroid.data

import android.util.ArrayMap
import java.util.concurrent.TimeUnit

class RateLimiter<in KEY> constructor(
        timeOut: Int,
        timeUnit: TimeUnit) {
    private val timeOut: Long = timeUnit.toMillis(timeOut.toLong())
    private val timeStamps = ArrayMap<KEY, Long>()


    @Synchronized
    fun shouldFetch(key: KEY): Boolean {
        var result = false
        val lastFetched = timeStamps[key]
        val now = System.currentTimeMillis()
        if (isTimeUp(lastFetched, now)) {
            timeStamps[key] = now
            result = true
        }

        return result
    }

    fun reset(key: KEY) {
        timeStamps.remove(key)
    }

    private fun isTimeUp(lastFetched: Long?, now: Long): Boolean {
        return lastFetched == null || (now - lastFetched) > timeOut
    }
}