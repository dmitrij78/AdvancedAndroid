package com.nitrosoft.ua.advancedandroid.models

import com.nitrosoft.ua.poweradapter.item.RecyclerItem

data class ContributorListItem(val contributor: Contributor) : RecyclerItem {

    override fun getId(): Long {
        return contributor.id
    }

    override fun renderKey(): String {
        return "CONTRIBUTOR"
    }
}