package com.nitrosoft.ua.advancedandroid.ui

interface ScreenNavigator {

    fun pop(): Boolean

    fun goToRepoDetails(repoOwner: String, repoName: String)
}