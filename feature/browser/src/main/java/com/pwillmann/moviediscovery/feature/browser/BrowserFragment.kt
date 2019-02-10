package com.pwillmann.moviediscovery.feature.browser

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.MvRx
import com.airbnb.mvrx.fragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.pwillmann.moviediscovery.core.kotterknife.bindView
import com.pwillmann.moviediscovery.epoxy.loadingRow
import com.pwillmann.moviediscovery.epoxy.titleRow
import com.pwillmann.moviediscovery.epoxy.tvItem
import com.pwillmann.moviediscovery.epoxy.v2.CoverItem_
import com.pwillmann.moviediscovery.epoxy.v2.gallery.GalleryImageCardItem_
import com.pwillmann.moviediscovery.epoxy.v2.gallery.galleryCarousel
import com.pwillmann.moviediscovery.epoxy.v2.gallery.withModelsFrom
import com.pwillmann.moviediscovery.feature.detail.DetailStateArgs
import com.pwillmann.moviediscovery.lib.arch.mvrx.MvRxEpoxyFragment
import com.pwillmann.moviediscovery.lib.arch.mvrx.carousel
import com.pwillmann.moviediscovery.lib.arch.mvrx.simpleController
import com.pwillmann.moviediscovery.lib.arch.mvrx.withModelsFrom
import com.pwillmann.moviediscovery.lib.datasource.tmdb.TMDBConfig
import timber.log.Timber
import javax.inject.Inject

class BrowserFragment : MvRxEpoxyFragment() {
    private val constraintLayout: ConstraintLayout by bindView(R.id.container)
    private val recyclerView: EpoxyRecyclerView by bindView(R.id.recycler_view)
    private val pullToRefreshLayout: SwipeRefreshLayout by bindView(R.id.swiperefresh)

    private val viewModel: BrowserViewModel by fragmentViewModel()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.browser_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var errorSnackbar: Snackbar? = null
        pullToRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            pullToRefreshLayout.isRefreshing = false
            if (errorSnackbar != null) {
                errorSnackbar!!.dismiss()
            }
        }

        viewModel.asyncSubscribe(BrowserState::popularTvShowsRequest, onFail = { error ->
            errorSnackbar = Snackbar.make(constraintLayout, R.string.browser_error_tvshows, Snackbar.LENGTH_INDEFINITE)
            errorSnackbar!!.setAction(R.string.browser_error_retry) { _ -> viewModel.refresh() }
            errorSnackbar!!.show()

            Timber.w(error, "Tv Shows popularTvShowsRequest failed")
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        pullToRefreshLayout.setOnRefreshListener { }
        super.onDestroyView()
    }

    private fun navigateTo(@IdRes actionId: Int, arg: Parcelable? = null) {
        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        findNavController().navigate(actionId, bundle)
    }

    override fun recyclerView(): EpoxyRecyclerView = recyclerView

    override fun epoxyController() = simpleController(viewModel) { state ->
        if (state.popularTvShowsResponse == null) {
            loadingRow {
                // Changing the ID will force it to rebind when new data is loaded even if it is
                // still on screen which will ensure that we trigger loading again.
                id("loading")
            }
            return@simpleController
        }

        galleryCarousel {
            id("carousel")
            padding(Carousel.Padding.dp(8, 0))

            withModelsFrom(state.popularTvShowsResponse.results) {
                GalleryImageCardItem_()
                        .id("carousel-item-${it.id}")
                        .url("${TMDBConfig.tmdbImageBaseUrl}/${TMDBConfig.posterSizes[TMDBConfig.ImageSize.MEDIUM.toString()]}/${it.posterPath}")
            }
        }

        titleRow {
            id("title")
            title(R.string.browser_title)
        }

        if (state.topRatedTvShowsResponse == null) {
            loadingRow {
                // Changing the ID will force it to rebind when new data is loaded even if it is
                // still on screen which will ensure that we trigger loading again.
                id("loading")
            }
            return@simpleController
        }
        carousel {
            id("top-rated-carousel")
            numViewsToShowOnScreen(3.5f)
            withModelsFrom(state.topRatedTvShowsResponse.results) {
                CoverItem_()
                        .id("top-rated-${it.id}")
                        .imageUrl("${TMDBConfig.tmdbImageBaseUrl}/${TMDBConfig.posterSizes[TMDBConfig.ImageSize.MEDIUM.toString()]}/${it.posterPath}")
                        .title(it.name)
            }
        }

        state.popularTvShowsResponse.results.forEach { tvShow ->
            tvItem {
                id(tvShow.id)
                title(tvShow.name)
                summary(tvShow.overview)
                rating(tvShow.voteAverage.toString())
                voteCount(tvShow.voteCount.toString())
                posterImageUrl("${TMDBConfig.tmdbImageBaseUrl}/${TMDBConfig.posterSizes[TMDBConfig.ImageSize.SMALL.toString()]}/${tvShow.posterPath}")
                clickListener { _ ->
                    navigateTo(R.id.action_browserFragment_to_detailFragment,
                            DetailStateArgs(tvShow.id))
                }
            }
        }

        loadingRow {
            // Changing the ID will force it to rebind when new data is loaded even if it is
            // still on screen which will ensure that we trigger loading again.
            id("loading${state.popularTvShowsResponse.page}")
            onBind { _, _, _ -> viewModel.fetchNextPage() }
        }
    }
}
