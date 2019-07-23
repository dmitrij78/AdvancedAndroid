package com.nitrosoft.ua.advancedandroid.details

import com.jakewharton.rxrelay2.BehaviorRelay
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import javax.inject.Inject

@ScreenScope
class RepoDetailsViewModel @Inject constructor() {

    private val dateTimeFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("MMM dd, yyyy")

    private val detailsStateRelay: BehaviorRelay<RepoDetailsState> = BehaviorRelay.create()
    private val contributorStateRelay: BehaviorRelay<ContributorState> = BehaviorRelay.create()

    init {
        detailsStateRelay.accept(RepoDetailsState(true))
        contributorStateRelay.accept(ContributorState(true))
    }

    fun details(): Observable<RepoDetailsState> {
        return detailsStateRelay
    }

    fun contributors(): Observable<ContributorState> {
        return contributorStateRelay
    }

    fun processRepo(): Consumer<Repo> {
        return Consumer { repo ->
            detailsStateRelay.accept(RepoDetailsState(
                    loading = false,
                    name = repo.name,
                    description = repo.description,
                    createDate = repo.createdDate.format(dateTimeFormatter),
                    updateDate = repo.updatedDate.format(dateTimeFormatter))
            )
        }
    }

    fun processContributors(): Consumer<List<Contributor>> {
        return Consumer {
            contributorStateRelay.accept(ContributorState(loading = false))
        }
    }

    fun contributorsLoaded(): Consumer<Any> {
        return Consumer {
            contributorStateRelay.accept(ContributorState(loading = false))
        }
    }

    fun detailsError(): Consumer<Throwable> {
        return Consumer { throwable ->
            Timber.e(throwable, "Error loading repo details")

            detailsStateRelay.accept(RepoDetailsState(
                    loading = false,
                    errorRes = R.string.api_error_single_repo)
            )
        }
    }

    fun contributorsError(): Consumer<Throwable> {
        return Consumer { throwable ->
            Timber.e(throwable, "Error loading contributors")

            contributorStateRelay.accept(ContributorState(
                    loading = false,
                    errorRes = R.string.api_error_contributors)
            )
        }
    }
}