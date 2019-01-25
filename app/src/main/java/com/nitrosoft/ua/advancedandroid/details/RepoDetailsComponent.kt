package com.nitrosoft.ua.advancedandroid.details

import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Named

@ScreenScope
@Subcomponent
interface RepoDetailsComponent : AndroidInjector<RepoDetailsController> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<RepoDetailsController>() {

        @BindsInstance
        abstract fun bindRepoOwner(@Named("repo_owner") repoOwner: String)

        @BindsInstance
        abstract fun bindRepoName(@Named("repo_name") repoName: String)

        override fun seedInstance(instance: RepoDetailsController?) {
            instance?.args?.getString(RepoDetailsController.REPO_NAME_KEY)?.let { bindRepoName(it) }
            instance?.args?.getString(RepoDetailsController.REPO_OWNER_KEY)?.let { bindRepoOwner(it) }
        }
    }
}