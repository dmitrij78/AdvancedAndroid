package com.nitrosoft.ua.advancedandroid.details

import android.os.Bundle
import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseController

class RepoDetailsController(bundle: Bundle) : BaseController() {

    companion object {
        const val REPO_NAME_KEY = "repo_name"
        const val REPO_OWNER_KEY = "repo_owner"

        fun newInstance(name: String, owner: String): Controller {
            val bundle = Bundle()
            bundle.putString(REPO_NAME_KEY, name)
            bundle.putString(REPO_OWNER_KEY, owner)
            return RepoDetailsController(bundle)
        }
    }

    override fun layoutRes(): Int {
        return R.layout.scree_repo_details
    }
}