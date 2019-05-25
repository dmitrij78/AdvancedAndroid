package com.nitrosoft.ua.advancedandroid.util

import butterknife.Unbinder
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ButterKnifeUtils @Inject constructor() {

    fun unbind(unBinder: Unbinder?) {
        if (unBinder != null) {
            try {
                unBinder.unbind()
            } catch (e: IllegalStateException) {
                Timber.e(e, "Error unbinding view")
            }
        }
    }
}