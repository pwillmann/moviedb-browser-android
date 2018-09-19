package com.pwillmann.moviediscovery.features.browser

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.pwillmann.moviediscovery.core.BaseFragment
import com.pwillmann.moviediscovery.core.simpleController
import com.pwillmann.moviediscovery.views.basicRow
import com.pwillmann.moviediscovery.views.loadingRow
import com.pwillmann.moviediscovery.views.marquee

private const val TAG = "BrowserFragment"

class BrowserFragment : BaseFragment() {

    /**
     * This will get or create a new ViewModel scoped to this Fragment. It will also automatically
     * subscribe to all state changes and call [invalidate] which we have wired up to
     * call [buildModels] in [BaseFragment].
     */
    private val viewModel: BrowserViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**
         * Use viewModel.subscribe to listen for changes. The parameter is a shouldUpdate
         * function that is given the old state and new state and returns whether or not to
         * call the subscriber. onSuccess, onFail, and propertyWhitelist ship with MvRx.
         */
        viewModel.asyncSubscribe(BrowserState::request, onFail = { error ->
            Snackbar.make(coordinatorLayout, "Tv Shows request failed.", Snackbar.LENGTH_INDEFINITE)
                    .show()
            Log.w(TAG, "Tv Shows request failed", error)
        })
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        marquee {
            id("marquee")
            title("Dad Jokes")
        }

        state.tvShowsResponse.results.forEach { tvShow ->
            basicRow {
                id(tvShow.id)
                title(tvShow.name)
                clickListener { _ -> }
            }
        }

        loadingRow {
            // Changing the ID will force it to rebind when new data is loaded even if it is
            // still on screen which will ensure that we trigger loading again.
            id("loading${state.tvShowsResponse.page}")
            onBind { _, _, _ -> viewModel.fetchNextPage() }
        }
    }
}