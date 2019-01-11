package com.pwillmann.moviediscovery.feature.detail

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRx
import com.airbnb.mvrx.fragmentViewModel
import com.pwillmann.moviediscovery.core.GlideApp
import com.pwillmann.moviediscovery.core.simpleController
import com.pwillmann.moviediscovery.model.TvShow
import com.pwillmann.moviediscovery.service.remote.TMDBBaseApiClient
import com.pwillmann.moviediscovery.view.LoadingRowModel_
import com.pwillmann.moviediscovery.view.TvItemCompactModel_
import com.pwillmann.moviediscovery.view.card.ItemType
import com.pwillmann.moviediscovery.view.card.cardCarousel
import com.pwillmann.moviediscovery.view.card.cardLoading
import com.pwillmann.moviediscovery.view.card.cardSpace
import com.pwillmann.moviediscovery.view.card.cardText
import com.pwillmann.moviediscovery.view.card.cardTitle
import com.pwillmann.moviediscovery.view.loadingRow

private const val TAG = "BrowserFragment"

class DetailFragment : BaseMvRxFragment() {
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var recyclerView: EpoxyRecyclerView
    private lateinit var backgroundImageView: ImageView
    private lateinit var ratingView: ConstraintLayout

    private lateinit var yearTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var genresTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var voteCountTextView: TextView
    private lateinit var progressBar: ProgressBar

    private val epoxyController by lazy { epoxyController() }
    private val viewModel: DetailViewModel by fragmentViewModel()
    var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment_main, container, false).apply {
            recyclerView = findViewById(R.id.recycler_view)
            constraintLayout = findViewById(R.id.container)
            backgroundImageView = findViewById(R.id.background)
            yearTextView = findViewById(R.id.year)
            nameTextView = findViewById(R.id.name)
            genresTextView = findViewById(R.id.genres)
            ratingTextView = findViewById(R.id.rating)
            voteCountTextView = findViewById(R.id.voteCount)
            progressBar = findViewById(R.id.progressbar)
            ratingView = findViewById(R.id.ratingView)

            recyclerView.setController(epoxyController)
        }
    }

    override fun invalidate() {
        recyclerView.requestModelBuild()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ratingView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        viewModel.asyncSubscribe(DetailState::tvShowDetailRequest, onFail = { error ->
            errorSnackbar = Snackbar.make(constraintLayout, R.string.detail_error_tvshow, Snackbar.LENGTH_INDEFINITE)
            errorSnackbar!!.setAction(R.string.detail_error_retry) { _ -> viewModel.fetchTvShowData() }
            errorSnackbar!!.show()
            Log.w(TAG, "Tv Shows request failed", error)
        }, onSuccess = { tvShow ->
            setupBackgroundImage(tvShow)
            setupHeader(tvShow)
        })
        viewModel.asyncSubscribe(DetailState::similarTvShowsRequest, onFail = { error ->
            errorSnackbar = Snackbar.make(constraintLayout, R.string.detail_error_similar, Snackbar.LENGTH_INDEFINITE)
            errorSnackbar!!.setAction(R.string.detail_error_retry) { _ -> viewModel.refreshSimilarTvShows() }
            errorSnackbar!!.show()
            Log.w(TAG, "Similar TV Shows request failed", error)
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        if (errorSnackbar != null && errorSnackbar!!.isShown) {
            errorSnackbar!!.dismiss()
        }
        super.onDestroyView()
    }

    private fun setupBackgroundImage(tvShow: TvShow) {
        GlideApp.with(this)
                .asBitmap()
                .load("${TMDBBaseApiClient.tmdbImageBaseUrl}/${TMDBBaseApiClient.posterSizes[TMDBBaseApiClient.Companion.ImageSize.LARGE.toString()]}/${tvShow.backdropPath}")
                .placeholder(R.color.black)
                .into(backgroundImageView)
    }

    private fun setupHeader(tvShow: TvShow) {
        yearTextView.text = android.text.format.DateFormat.format("yyyy", tvShow.firstAirDate)
        nameTextView.text = tvShow.name
        if (tvShow.genres != null) {
            val genreString = tvShow.genres!!
                    .map { genre -> genre.name }
                    .joinToString(separator = "/ ") { s: String -> s.toUpperCase() }
            genresTextView.text = genreString
        }
        ratingTextView.text = tvShow.voteAverage.toString()
        voteCountTextView.text = tvShow.voteCount.toString()
        ratingView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun navigateTo(@IdRes actionId: Int, arg: Parcelable? = null) {
        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        findNavController().navigate(actionId, bundle)
    }

    private fun epoxyController() = simpleController(viewModel) { state ->
        val tvShow = state.tvShow
        if (tvShow == null) {
            loadingRow {
                id("loading")
            }
            return@simpleController
        }

        cardTitle {
            id("${tvShow.id}-about-title")
            title("About:")
            itemType(ItemType.TOP)
        }

        cardText {
            id("${tvShow.id}-about-text")
            text(tvShow.overview)
            itemType(ItemType.NORMAL)
        }
        cardSpace {
            id("about-space")
            itemType(ItemType.NORMAL)
        }

        val similarTvShowsResponse = state.similarTvShowsResponse
        if (similarTvShowsResponse == null) {
            cardLoading {
                id("${tvShow.id}-loading")
                itemType(ItemType.BOTTOM)
            }
            return@simpleController
        }
        val similarTvShows = similarTvShowsResponse.results
        val carouselModels: MutableList<EpoxyModel<*>> = mutableListOf()

        for (similarShow in similarTvShows) {
            carouselModels.add(TvItemCompactModel_()
                    .id("${tvShow.id}-similarshow-${similarShow.id}")
                    .title(similarShow.name)
                    .rating(similarShow.voteAverage.toString())
                    .voteCount(similarShow.voteCount.toString())
                    .posterImageUrl("${TMDBBaseApiClient.tmdbImageBaseUrl}/${TMDBBaseApiClient.posterSizes[TMDBBaseApiClient.Companion.ImageSize.SMALL.toString()]}/${similarShow.posterPath}")
                    .clickListener { _ -> navigateTo(R.id.action_detailFragment_to_detailFragment, DetailStateArgs(similarShow.id)) })
        }
        if (similarTvShowsResponse.page < similarTvShowsResponse.totalPages) {
            carouselModels.add(
                    LoadingRowModel_()
                            .id("${tvShow.id}-load-more-similar-shows")
                            .onBind { _, _, _ -> viewModel.fetchNextSimilarTvShows() })
        }

        cardTitle {
            id("${tvShow.id}-similar_shows-title")
            title("Similar TV Shows:")
            itemType(ItemType.NORMAL)
        }

        cardCarousel {
            id("${tvShow.id}-similar_shows-item")
            itemType(ItemType.BOTTOM)
            carouselId("${tvShow.id}-similar_shows-carousel")
            models(carouselModels)
            numViewsToShowOnScreen(1.5f)
        }
    }
}