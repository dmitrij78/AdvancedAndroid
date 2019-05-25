package com.nitrosoft.ua.advancedandroid.details

import com.nitrosoft.ua.advancedandroid.base.ScreenModule
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Named

@ScreenScope
@Subcomponent(modules = [RepoDetailsComponent.RepoDetailsModule::class, ScreenModule::class])
interface RepoDetailsComponent : AndroidInjector<RepoDetailsController> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<RepoDetailsController>

    @Module
    abstract class RepoDetailsModule {
        @Module
        companion object {

            @JvmStatic
            @Provides
            @Named("repo_owner")
            fun providesRepoOwner(instance: RepoDetailsController?): String {
                return instance?.args?.getString(RepoDetailsController.REPO_OWNER_KEY)!!
            }

            @JvmStatic
            @Provides
            @Named("repo_name")
            fun providesRepoName(instance: RepoDetailsController?): String {
                return instance?.args?.getString(RepoDetailsController.REPO_NAME_KEY)!!
            }
        }
    }
}