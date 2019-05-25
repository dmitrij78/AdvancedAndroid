package com.nitrosoft.ua.advancedandroid.details

import com.nitrosoft.ua.advancedandroid.base.ScreenModule
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ScreenScope
@Subcomponent(modules = [RepoDetailsModule::class, ScreenModule::class, DetailsScreenModule::class])
interface RepoDetailsComponent : AndroidInjector<RepoDetailsController> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<RepoDetailsController>

    /*@Module
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
    }*/
}