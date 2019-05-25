package com.nitrosoft.ua.advancedandroid.lifecycle

import android.view.View

abstract class ScreenLifecycleTask {

    /*
    * Callback received when a Screen becomes the visible screen
    */
    abstract fun onEnterScope(view: View?)

    /*
    * Callback received when a Screen is either popped or moved to back stack
    */
    abstract fun onExitScope()

    /*
    * Callback received when a Screen is destroyed and wil not be coming back (except new instance). This should be used to clear any {@link ActivityScope} connections
    */
    abstract fun onDestroy()
}
