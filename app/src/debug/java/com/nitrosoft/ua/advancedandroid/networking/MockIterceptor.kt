package com.nitrosoft.ua.advancedandroid.networking

import com.nitrosoft.ua.advancedandroid.settings.DebugPreferences
import okhttp3.*
import javax.inject.Inject

class MockIterceptor @Inject constructor(
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
                        .body(ResponseBody.create(MediaType.parse("text/json"), mockResponse))
                        .build()
            }
        }
        return chain.proceed(chain.request())
    }
}