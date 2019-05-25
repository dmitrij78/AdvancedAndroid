package com.nitrosoft.ua.advancedandroid.details

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class RepoDetailsModule {
    @Module
    companion object {

        @JvmStatic
        @Provides
        @Named(RepoDetailsController.REPO_OWNER_KEY)
        fun providesRepoOwner(instance: RepoDetailsController?): String {
            return instance?.args?.getString(RepoDetailsController.REPO_OWNER_KEY)!!
        }

        @JvmStatic
        @Provides
        @Named(RepoDetailsController.REPO_NAME_KEY)
        fun providesRepoName(instance: RepoDetailsController?): String {
            return instance?.args?.getString(RepoDetailsController.REPO_NAME_KEY)!!
        }
    }
}