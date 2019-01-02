package com.nitrosoft.ua.advancedandroid.data

import dagger.Binds
import dagger.Module

@Module
abstract class TestRepoServiceModule {
    @Binds
    abstract fun bindRepoService(service: TestRepoService): RepoService
}