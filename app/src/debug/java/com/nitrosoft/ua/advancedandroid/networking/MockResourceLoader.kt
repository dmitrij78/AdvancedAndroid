package com.nitrosoft.ua.advancedandroid.networking

import android.content.Context
import androidx.annotation.Nullable
import timber.log.Timber
import java.io.IOException

class MockResourceLoader {

    companion object {
        @Nullable
        fun getResponseString(context: Context, method: String, endpointParts: Array<String>): String? {
            try {
                var currentPath = "mock"
                var mockList: Set<String> = context.assets.list(currentPath)?.toSet()!!
                for (endpoint in endpointParts) {
                    if (mockList.contains(endpoint)) {
                        currentPath = "$currentPath/$endpoint"
                        mockList = context.assets.list(currentPath)?.toSet()!!
                    }
                }

                var finalPath: String? = null
                for (path in mockList) {
                    if (path.contains(method.lowercase())) {
                        finalPath = "$currentPath/$path"
                        break
                    }
                }

                if (finalPath != null) {
                    return responseFromPath(context, finalPath)
                }

                return null

            } catch (e: IOException) {
                Timber.e(e, "Error loading mock response from assets")
                return null
            }
        }

        private fun responseFromPath(context: Context, path: String): String? {
            var allLines: String? = null
            try {
                allLines = context.assets.open(path).bufferedReader().readText()
            } catch (e: IOException) {
                Timber.e(e, "Error reading mock response")
            }
            return allLines
        }
    }
}