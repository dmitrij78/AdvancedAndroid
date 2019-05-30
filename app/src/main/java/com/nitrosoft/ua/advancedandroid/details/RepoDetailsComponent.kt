package com.nitrosoft.ua.advancedandroid.details

import com.nitrosoft.ua.advancedandroid.base.ScreenModule
import com.nitrosoft.ua.advancedandroid.di.ScreenComponent
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ScreenScope
@Subcomponent(modules = [RepoDetailsModule::class, ScreenModule::class, DetailsScreenModule::class])
interface RepoDetailsComponent : ScreenComponent<RepoDetailsController> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<RepoDetailsController>
}