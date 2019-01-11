package com.pwillmann.moviediscovery.feature.detail

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.PersistState
import com.airbnb.mvrx.Uninitialized
import com.pwillmann.moviediscovery.core.MvRxViewModel
import com.pwillmann.moviediscovery.model.PaginatedListResponse
import com.pwillmann.moviediscovery.model.TvShow
import com.pwillmann.moviediscovery.model.TvShowCompact
import com.pwillmann.moviediscovery.model.mergeWith
import com.pwillmann.moviediscovery.service.remote.TvShowsService
import kotlinx.android.parcel.Parcelize
import org.koin.android.ext.android.inject

@SuppressLint("ParcelCreator")
@Parcelize
data class DetailStateArgs(val tvShowId: Long) : Parcelable

data class DetailState(
    @PersistState val tvShowId: Long = 0,
    val tvShowDetailRequest: Async<TvShow> = Uninitialized,
    val tvShow: TvShow? = null,
    val similarTvShowsRequest: Async<PaginatedListResponse<TvShowCompact>> = Uninitialized,
    val similarTvShowsResponse: PaginatedListResponse<TvShowCompact>? = PaginatedListResponse(0, 0, 0, emptyList())
) : MvRxState {
    constructor(args: DetailStateArgs) : this(tvShowId = args.tvShowId)
}

/**
 * initialState *must* be implemented as a constructor parameter.
 */
class DetailViewModel(
    initialState: DetailState,
    private val tvShowsService: TvShowsService
) : MvRxViewModel<DetailState>(initialState) {

    init {
        fetchTvShowData()
        fetchNextSimilarTvShows()
    }

    fun fetchTvShowData() = withState { state ->
        if (state.tvShowDetailRequest is Loading) return@withState
        tvShowsService
                .getDetails(tvShowId = state.tvShowId)
                .execute {
                    copy(tvShowDetailRequest = it, tvShow = if (it.complete) it() else state.tvShow)
                }
    }

    /**
     * Performs a clean reload of the similar tv shows data by disposing all of the already loaded data and
     * fetching them again
     */
    fun refreshSimilarTvShows() = withState { state ->
        if (state.similarTvShowsRequest is Loading) return@withState
        tvShowsService
                .getSimilarShows(tvShowId = state.tvShowId, page = 1)
                .execute {
                    copy(similarTvShowsRequest = it, similarTvShowsResponse = if (it.complete) it() else it()
                            ?: similarTvShowsResponse)
                }
    }

    /**
     * behaves like refresh() for the first time its called, every subsequent call will fetch one more
     * page and merge it into the already available list of similar tv shows
     */
    fun fetchNextSimilarTvShows() = withState { state ->
        if (state.similarTvShowsRequest is Loading) return@withState
        if (state.similarTvShowsResponse != null && (state.similarTvShowsResponse.page >= state.similarTvShowsResponse.totalPages && state.similarTvShowsResponse.page != 0)) return@withState

        val currentPage = state.similarTvShowsResponse?.page ?: 0
        tvShowsService
                .getSimilarShows(tvShowId = state.tvShowId, page = currentPage + 1)
                .execute {
                    copy(similarTvShowsRequest = it, similarTvShowsResponse = state.similarTvShowsResponse.mergeWith(it()))
                }
    }

    /**
     * If you implement MvRxViewModelFactory in your companion object, MvRx will use that to create
     * your ViewModel. You can use this to achieve constructor dependency injection with MvRx.
     *
     * @see MvRxViewModelFactory
     */
    companion object : MvRxViewModelFactory<DetailState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: DetailState): BaseMvRxViewModel<DetailState> {
            val service: TvShowsService by activity.inject()
            return DetailViewModel(state, service)
        }
    }
}
