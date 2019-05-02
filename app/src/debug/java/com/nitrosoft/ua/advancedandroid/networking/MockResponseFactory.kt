package com.nitrosoft.ua.advancedandroid.networking

import android.content.Context
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Named

class MockResponseFactory @Inject constructor(
        private val context: Context,
        @Named("base_url") private val baseUrl: String) {

    private var startIndex: Int = baseUrl.length

    private fun getEndPoint(request: Request): String {
        val url: String = request.url().toString()
        val queryParamStart = url.indexOf("?")
        return if (queryParamStart == -1) url.substring(startIndex) else url.substring(startIndex, queryParamStart)
    }

    fun getMockResponse(request: Request): String? {
        val endpointParts = getEndPoint(request).split("/")
        return MockResourceLoader.getResponseString(context, request.method(), endpointParts.toTypedArray())
    }
}