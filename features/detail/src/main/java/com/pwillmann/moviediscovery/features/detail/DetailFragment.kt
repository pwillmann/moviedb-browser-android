package com.pwillmann.moviediscovery.features.detail

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
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRx
import com.airbnb.mvrx.fragmentViewModel
import com.pwillmann.moviediscovery.core.carousel
import com.pwillmann.moviediscovery.core.simpleController
import com.pwillmann.moviediscovery.views.BasicRowModel_
import com.pwillmann.moviediscovery.views.LoadingRowModel_
import com.pwillmann.moviediscovery.views.basicRow
import com.pwillmann.moviediscovery.views.loadingRow
import com.pwillmann.moviediscovery.views.marquee

private const val TAG = "BrowserFragment"

class DetailFragment : BaseMvRxFragment() {
    protected lateinit var constraintLayout: ConstraintLayout
    protected lateinit var recyclerView: EpoxyRecyclerView
    protected lateinit var pullToRefreshLayout: SwipeRefreshLayout

    protected val epoxyController by lazy { epoxyController() }
    private val viewModel: DetailViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false).apply {
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
        pullToRefreshLayout.setOnRefreshListener {
            viewModel.fetchTvShowData()
            viewModel.refreshSimilarTvShows()
        }

        viewModel.asyncSubscribe(DetailState::tvShowDetailRequest, onFail = { error ->
            Snackbar.make(constraintLayout, "Tv Shows request failed.", Snackbar.LENGTH_INDEFINITE)
                    .show()
            Log.w(TAG, "Tv Shows request failed", error)
        })
        viewModel.asyncSubscribe(DetailState::similarTvShowsRequest, onFail = { error ->
            Snackbar.make(constraintLayout, "Similar TV Shows request failed.", Snackbar.LENGTH_INDEFINITE)
                    .show()
            Log.w(TAG, "Similar TV Shows request failed", error)
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

    protected fun navigateTo(@IdRes actionId: Int, arg: Parcelable? = null) {
        /**
         * If we put a parcelable arg in [MvRx.KEY_ARG] then MvRx will attempt to call a secondary
         * constructor on any MvRxState objects and pass in this arg directly.
         * @see [com.pwillmann.moviediscovery.app.features.dadjoke.DadJokeDetailState]
         */
        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        findNavController().navigate(actionId, bundle)
    }

    fun epoxyController() = simpleController(viewModel) { state ->
        val tvShow = state.tvShow
        if (tvShow == null) {
            loadingRow {
                // Changing the ID will force it to rebind when new data is loaded even if it is
                // still on screen which will ensure that we trigger loading again.
                id("loading")
            }
            return@simpleController
        }
        marquee {
            id("marquee")
            title(tvShow.name)
        }

        basicRow {
            id(tvShow.id)
            title(tvShow.originalName)
            subtitle(tvShow.overview)
            clickListener { _ -> }
        }

        val similarTvShowsResponse = state.similarTvShowsResponse
        if (similarTvShowsResponse == null) {
            loadingRow {
                id("loading")
            }
            return@simpleController
        }
        pullToRefreshLayout.isRefreshing = false

        val similarTvShows = similarTvShowsResponse.results
        val carouselModels: MutableList<EpoxyModel<*>> = mutableListOf()

        for (similarShow in similarTvShows) {
            carouselModels.add(BasicRowModel_()
                    .id(similarShow.toString())
                    .title(similarShow.name))
        }
        if (similarTvShowsResponse.page < similarTvShowsResponse.totalPages) {
            carouselModels.add(
                    LoadingRowModel_()
                            .id("load-more-similar-shows")
                            .onBind { _, _, _ -> viewModel.fetchNextSimilarTvShows() })
        }

        carousel {
            id("shifts-carousel")
            models(carouselModels)
            numViewsToShowOnScreen(1.5f)
        }
    }
}