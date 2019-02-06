package com.nitrosoft.ua.advancedandroid.data

import org.junit.Test
import org.mockito.Mock
import javax.inject.Provider

class RepoRepositoryTest {

    @Mock
    lateinit var repoRequesterProvider: Provider<RepoRequester>
    @Mock
    lateinit var repoRequester: RepoRequester

    @Test
    fun getTrendingRepos() {
    }

    @Test
    fun getRepo() {
    }

    @Before
    fun setUp() {
    }
}