package com.nitrosoft.ua.advancedandroid.networking

import com.nitrosoft.ua.advancedandroid.settings.DebugPreferences
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class MockInterceptor @Inject constructor(
        private val mockResponseFactory: MockResponseFactory,
        private val debugPreferences: DebugPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (debugPreferences.mockResponseEnabled()) {
            val mockResponse = mockResponseFactory.getMockResponse(chain.request())
            if (mockResponse != null) {
                return Response.Builder()
                        .message("")
                        .protocol(Protocol.HTTP_1_1)
                        .request(chain.request())
                        .code(200)
                        .body(mockResponse.toResponseBody("text/json".toMediaType()))
                        .build()
            }
        }
        return chain.proceed(chain.request())
    }
}