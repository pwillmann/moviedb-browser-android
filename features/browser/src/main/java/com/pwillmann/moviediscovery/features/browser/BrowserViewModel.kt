package com.pwillmann.moviediscovery.features.browser

import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.pwillmann.moviediscovery.core.MvRxViewModel
import com.pwillmann.moviediscovery.models.PaginatedListResponse
import com.pwillmann.moviediscovery.models.TvShowCompact
import com.pwillmann.moviediscovery.models.mergeWith
import com.pwillmann.moviediscovery.network.TvShowsService
import org.koin.android.ext.android.inject

data class BrowserState(
    val tvShowsResponse: PaginatedListResponse<TvShowCompact>? = PaginatedListResponse(0, 0, 0, emptyList()),
    val request: Async<PaginatedListResponse<TvShowCompact>> = Uninitialized
) : MvRxState

class BrowserViewModel(
    initialState: BrowserState,
    private val tvShowsService: TvShowsService
) : MvRxViewModel<BrowserState>(initialState) {

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
    companion object : MvRxViewModelFactory<BrowserState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: BrowserState): BaseMvRxViewModel<BrowserState> {
            val service: TvShowsService by activity.inject()
            return BrowserViewModel(state, service)
        }
    }
}
