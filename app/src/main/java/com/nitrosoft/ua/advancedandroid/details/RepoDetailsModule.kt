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
        @Named(RepoDetailsFragment.REPO_OWNER_KEY)
        fun providesRepoOwner(instance: RepoDetailsFragment?): String {
            return instance?.arguments?.getString(RepoDetailsFragment.REPO_OWNER_KEY)!!
        }

        @JvmStatic
        @Provides
        @Named(RepoDetailsFragment.REPO_NAME_KEY)
        fun providesRepoName(instance: RepoDetailsFragment?): String {
            return instance?.arguments?.getString(RepoDetailsFragment.REPO_NAME_KEY)!!
        }
    }
}