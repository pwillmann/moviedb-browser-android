package com.pwillmann.moviediscovery.feature.browser

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.pwillmann.moviediscovery.lib.arch.mvrx.MvRxViewModel
import com.pwillmann.moviediscovery.lib.datasource.tmdb.model.PaginatedListResponse
import com.pwillmann.moviediscovery.lib.datasource.tmdb.model.mergeWith
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.RetrofitTvShowApi
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.model.TvShowCompact
import javax.inject.Inject

data class BrowserState(
    val popularTvShowsResponse: PaginatedListResponse<TvShowCompact>? = PaginatedListResponse(0, 0, 0, emptyList()),
    val popularTvShowsRequest: Async<PaginatedListResponse<TvShowCompact>> = Uninitialized,
    val topRatedTvShowsResponse: PaginatedListResponse<TvShowCompact>? = PaginatedListResponse(0, 0, 0, emptyList()),
    val topRatedTvShowsRequest: Async<PaginatedListResponse<TvShowCompact>> = Uninitialized
) : MvRxState

class BrowserViewModel @Inject constructor(
    private val tvShowsService: RetrofitTvShowApi
) : MvRxViewModel<BrowserState>(BrowserState()) {

    init {
        fetchNextPage()
        fetchTopRatedTvShows()
    }

    /**
     * Performs a clean reload of the tv shows data by disposing all of the already loaded data and
     * fetching them again
     */
    fun refresh() = withState { state ->
        if (state.popularTvShowsRequest is Loading) return@withState
        tvShowsService
                .getPopularTvShows(page = 1)
                .execute {
                    copy(popularTvShowsRequest = it, popularTvShowsResponse = if (it.complete && it is Success) it() else it()
                            ?: popularTvShowsResponse)
                }
    }

    /**
     * behaves like refresh() for the first time its called, every subsequent call will fetch one more
     * page and merge it into the already available list of tv shows
     */
    fun fetchNextPage() = withState { state ->
        if (state.popularTvShowsRequest is Loading) return@withState
        if (state.popularTvShowsResponse != null && (state.popularTvShowsResponse.page >= state.popularTvShowsResponse.totalPages && state.popularTvShowsResponse.page != 0)) return@withState

        val currentPage = state.popularTvShowsResponse?.page ?: 0
        tvShowsService
                .getPopularTvShows(page = currentPage + 1)
                .execute {
                    copy(popularTvShowsRequest = it, popularTvShowsResponse = state.popularTvShowsResponse.mergeWith(it()))
                }
    }

    fun fetchTopRatedTvShows() = withState { state ->
        if (state.topRatedTvShowsRequest is Loading) return@withState
        tvShowsService
                .getTopRatedTvShows(page = 1)
                .execute {
                    copy(topRatedTvShowsRequest = it, topRatedTvShowsResponse = if (it.complete && it is Success) it() else it()
                            ?: topRatedTvShowsResponse)
                }
    }

    /**
     * If you implement MvRxViewModelFactory in your companion object, MvRx will use that to create
     * your ViewModel. You can use this to achieve constructor dependency injection with MvRx.
     *
     * @see MvRxViewModelFactory
     */
    companion object : MvRxViewModelFactory<BrowserViewModel, BrowserState> {
        override fun create(viewModelContext: ViewModelContext, state: BrowserState): BrowserViewModel {
            return (viewModelContext as FragmentViewModelContext).fragment<BrowserFragment>().viewModelFactory.create(BrowserViewModel::class.java)
        }
    }
}
