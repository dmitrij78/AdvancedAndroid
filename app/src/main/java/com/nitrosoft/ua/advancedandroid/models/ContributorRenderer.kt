package com.nitrosoft.ua.advancedandroid.models

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.database.favorites.FavoriteService
import com.nitrosoft.ua.advancedandroid.databinding.ViewUserListItemBinding
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ContributorRenderer @Inject constructor(private val favoriteService: FavoriteService) :
    ItemRenderer<ContributorListItem> {

    override fun layoutRes(): Int {
        return R.layout.view_user_list_item
    }

    override fun createView(parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes(), parent, false)
        val binding = ViewUserListItemBinding.bind(view)
        view.tag = ViewBinder(binding, favoriteService)

        return view
    }

    override fun render(itemView: View, item: RecyclerItem) {
        val viewBinder = itemView.tag as ViewBinder
        viewBinder.bind(item as ContributorListItem)
    }

    @SuppressLint("CheckResult")
    class ViewBinder(
        private val binding: ViewUserListItemBinding,
        private val favoriteService: FavoriteService
    ) {
        private var contributor: Contributor? = null
        private var favoriteDisposable: Disposable? = null

        init {
            RxView.attachEvents(binding.contributorRootView)
                .subscribe {
                    if (it.view().isAttachedToWindow) {
                        listenForFavoriteChanges()
                    } else {
                        favoriteDisposable?.dispose()
                        favoriteDisposable = null
                    }
                }
        }

        private fun listenForFavoriteChanges() {
            favoriteDisposable = favoriteService.favoriteContributorIds()
                .filter { contributor != null }
                .map { favoriteIds -> favoriteIds.contains(contributor?.id) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isFavorite ->
                    binding.contributorRootView.setBackgroundColor(if (isFavorite) Color.YELLOW else Color.TRANSPARENT)
                }
        }

        fun bind(contributorListItem: ContributorListItem) {
            this.contributor = contributorListItem.contributor

            binding.userNameTv.text = contributorListItem.contributor.login

            binding.contributorRootView.setOnLongClickListener(View.OnLongClickListener {
                if (this.contributor != null) {
                    favoriteService.toggleFavorite(this.contributor!!)
                }
                return@OnLongClickListener true
            })

            Glide.with(binding.root.context)
                .load(contributorListItem.contributor.avatarUrl)
                .into(binding.avatarIv)
        }
    }
}