package com.pwillmann.moviediscovery.feature.detail

import android.annotation.SuppressLint
import android.os.Parcelable
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.PersistState
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.pwillmann.moviediscovery.lib.arch.mvrx.MvRxViewModel
import com.pwillmann.moviediscovery.lib.datasource.tmdb.model.PaginatedListResponse
import com.pwillmann.moviediscovery.lib.datasource.tmdb.model.mergeWith
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.RetrofitTvShowApi
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.model.TvShow
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.model.TvShowCompact
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class DetailStateArgs(val tvShowId: Long) : Parcelable

data class DetailState(
    @PersistState val tvShowId: Long = 0,
    val tvShowDetailRequest: Async<TvShow> = Uninitialized,
    val tvShow: TvShow? = null,
    val similarTvShowsRequest: Async<PaginatedListResponse<TvShowCompact>> = Uninitialized,
    val similarTvShowsResponse: PaginatedListResponse<TvShowCompact>? = PaginatedListResponse(0, 0, 0, emptyList())
) : MvRxState

/**
 * initialState *must* be implemented as a constructor parameter.
 */
class DetailViewModel @AssistedInject constructor(
    @Assisted initialViewState: DetailState,
    private val tvShowsService: RetrofitTvShowApi
) : MvRxViewModel<DetailState>(initialViewState) {

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
    companion object : MvRxViewModelFactory<DetailViewModel, DetailState> {
        override fun create(viewModelContext: ViewModelContext, state: DetailState): DetailViewModel {
            return (viewModelContext as FragmentViewModelContext).fragment<DetailFragment>().viewModelFactory.create(state)
        }

        override fun initialState(viewModelContext: ViewModelContext): DetailState {
            val state = DetailState(tvShowId = viewModelContext.args<DetailStateArgs>().tvShowId)
            return state
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialViewState: DetailState): DetailViewModel
    }
}
