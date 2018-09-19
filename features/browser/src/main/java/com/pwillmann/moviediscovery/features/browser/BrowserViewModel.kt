package com.pwillmann.moviediscovery.features.browser

import android.support.v4.app.FragmentActivity
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.pwillmann.moviediscovery.core.MvRxViewModel
import com.pwillmann.moviediscovery.models.PaginatedListResponse
import com.pwillmann.moviediscovery.models.TvShow
import com.pwillmann.moviediscovery.network.PopularTvShowsService
import org.koin.android.ext.android.inject

data class BrowserState(
    /** We use this request to store the list of all tv shows */
    val tvShowsResponse: PaginatedListResponse<TvShow> = PaginatedListResponse(0, 0, 0, emptyList()),
    /** We use this Async to keep track of the state of the current network request */
    val request: Async<PaginatedListResponse<TvShow>> = Uninitialized
) : MvRxState

/**
 * initialState *must* be implemented as a constructor parameter.
 */
class BrowserViewModel(
    initialState: BrowserState,
    private val popularTvShowsService: PopularTvShowsService
) : MvRxViewModel<BrowserState>(initialState) {

    init {
        fetchNextPage()
    }

    fun fetchNextPage() = withState { state ->
        if (state.request is Loading) return@withState
        if (state.tvShowsResponse.page >= state.tvShowsResponse.totalPages) return@withState

        popularTvShowsService
                .getPopularTvShows(page = state.tvShowsResponse.page + 1)
                .execute {
                    copy(request = it, tvShowsResponse = state.tvShowsResponse.copy(results = state.tvShowsResponse.results + (it()?.results
                            ?: emptyList())))
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
            val service: PopularTvShowsService by activity.inject()
            return BrowserViewModel(state, service)
        }
    }
}
