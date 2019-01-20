package com.pwillmann.moviediscovery.feature.browser

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.pwillmann.moviediscovery.core.mvrx.MvRxViewModel
import com.pwillmann.moviediscovery.model.PaginatedListResponse
import com.pwillmann.moviediscovery.model.TvShowCompact
import com.pwillmann.moviediscovery.model.mergeWith
import com.pwillmann.moviediscovery.service.remote.TvShowsService
import javax.inject.Inject

data class BrowserState(
    val tvShowsResponse: PaginatedListResponse<TvShowCompact>? = PaginatedListResponse(0, 0, 0, emptyList()),
    val request: Async<PaginatedListResponse<TvShowCompact>> = Uninitialized
) : MvRxState

class BrowserViewModel @Inject constructor(
    private val tvShowsService: TvShowsService
) : MvRxViewModel<BrowserState>(BrowserState()) {

    init {
        fetchNextPage()
    }

    /**
     * Performs a clean reload of the tv shows data by disposing all of the already loaded data and
     * fetching them again
     */
    fun refresh() = withState { state ->
        if (state.request is Loading) return@withState
        tvShowsService
                .getPopularTvShows(page = 1)
                .execute {
                    copy(request = it, tvShowsResponse = if (it.complete && it is Success) it() else it()
                            ?: tvShowsResponse)
                }
    }

    /**
     * behaves like refresh() for the first time its called, every subsequent call will fetch one more
     * page and merge it into the already available list of tv shows
     */
    fun fetchNextPage() = withState { state ->
        if (state.request is Loading) return@withState
        if (state.tvShowsResponse != null && (state.tvShowsResponse.page >= state.tvShowsResponse.totalPages && state.tvShowsResponse.page != 0)) return@withState

        val currentPage = state.tvShowsResponse?.page ?: 0
        tvShowsService
                .getPopularTvShows(page = currentPage + 1)
                .execute {
                    copy(request = it, tvShowsResponse = state.tvShowsResponse.mergeWith(it()))
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
