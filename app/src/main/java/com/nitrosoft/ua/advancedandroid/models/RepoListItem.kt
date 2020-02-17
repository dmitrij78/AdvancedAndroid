package com.nitrosoft.ua.advancedandroid.models

import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntity
import com.nitrosoft.ua.poweradapter.item.RecyclerItem

data class RepoListItem(val repo: RepoEntity) : RecyclerItem {

    override fun getId(): Long {
        return repo.id
    }

    override fun renderKey(): String {
        return "REPO"
    }
}