package com.nitrosoft.ua.advancedandroid.ui

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router

interface RouterProvider {

    fun getRouter(): Router

    fun initialScreen(): Controller
}