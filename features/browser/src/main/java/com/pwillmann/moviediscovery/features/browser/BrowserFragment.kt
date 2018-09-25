package com.pwillmann.moviediscovery.features.browser

import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.IdRes
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRx
import com.airbnb.mvrx.fragmentViewModel
import com.pwillmann.moviediscovery.core.simpleController
import com.pwillmann.moviediscovery.features.detail.DetailStateArgs
import com.pwillmann.moviediscovery.network.TMDBBaseApiClient
import com.pwillmann.moviediscovery.network.TMDBBaseApiClient.Companion.tmdbImageBaseUrl
import com.pwillmann.moviediscovery.views.loadingRow
import com.pwillmann.moviediscovery.views.titleRow
import com.pwillmann.moviediscovery.views.tvItem

private const val TAG = "BrowserFragment"

class BrowserFragment : BaseMvRxFragment() {
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var recyclerView: EpoxyRecyclerView
    private lateinit var pullToRefreshLayout: SwipeRefreshLayout

    private val epoxyController by lazy { epoxyController() }
    private val viewModel: BrowserViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.browser_fragment, container, false).apply {
            recyclerView = findViewById(R.id.recycler_view)
            constraintLayout = findViewById(R.id.container)
            pullToRefreshLayout = findViewById(R.id.swiperefresh)

            recyclerView.setController(epoxyController)
        }
    }

    override fun invalidate() {
        recyclerView.requestModelBuild()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var errorSnackbar: Snackbar? = null
        pullToRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            pullToRefreshLayout.isRefreshing = false
            if (errorSnackbar != null) {
                errorSnackbar!!.dismiss()
            }
        }

        viewModel.asyncSubscribe(BrowserState::request, onFail = { error ->
            errorSnackbar = Snackbar.make(constraintLayout, R.string.browser_error_tvshows, Snackbar.LENGTH_INDEFINITE)
            errorSnackbar!!.setAction(R.string.browser_error_retry) { _ -> viewModel.refresh() }
            errorSnackbar!!.show()

            Log.w(TAG, "Tv Shows request failed", error)
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        pullToRefreshLayout.setOnRefreshListener { }
        super.onDestroyView()
    }

    private fun navigateTo(@IdRes actionId: Int, arg: Parcelable? = null) {
        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        findNavController().navigate(actionId, bundle)
    }

    private fun epoxyController() = simpleController(viewModel) { state ->
        if (state.tvShowsResponse == null) {
            loadingRow {
                // Changing the ID will force it to rebind when new data is loaded even if it is
                // still on screen which will ensure that we trigger loading again.
                id("loading")
            }
            return@simpleController
        }
        titleRow {
            id("title")
            title(R.string.browser_title)
        }

        state.tvShowsResponse.results.forEach { tvShow ->
            tvItem {
                id(tvShow.id)
                title(tvShow.name)
                summary(tvShow.overview)
                rating(tvShow.voteAverage.toString())
                voteCount(tvShow.voteCount.toString())
                posterImageUrl("$tmdbImageBaseUrl/${TMDBBaseApiClient.posterSizes[TMDBBaseApiClient.Companion.ImageSize.SMALL.toString()]}/${tvShow.posterPath}")
                clickListener { _ ->
                    navigateTo(R.id.action_browserFragment_to_detailFragment,
                            DetailStateArgs(tvShow.id))
                }
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