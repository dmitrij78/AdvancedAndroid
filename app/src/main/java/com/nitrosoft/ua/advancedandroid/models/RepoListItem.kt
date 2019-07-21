package com.nitrosoft.ua.advancedandroid.models

import com.nitrosoft.ua.poweradapter.item.RecyclerItem

data class RepoListItem(val repo: Repo) : RecyclerItem {

    override fun getId(): Long {
        return repo.id
    }

    override fun renderKey(): String {
        return "REPO"
    }
}