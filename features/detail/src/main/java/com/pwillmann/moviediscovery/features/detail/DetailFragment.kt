package com.pwillmann.moviediscovery.features.detail

import android.graphics.Bitmap
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pwillmann.moviediscovery.core.GlideApp
import com.pwillmann.moviediscovery.core.carousel
import com.pwillmann.moviediscovery.core.simpleController
import com.pwillmann.moviediscovery.models.TvShow
import com.pwillmann.moviediscovery.network.TMDBBaseApiClient
import com.pwillmann.moviediscovery.views.LoadingRowModel_
import com.pwillmann.moviediscovery.views.TvItemCompactModel_
import com.pwillmann.moviediscovery.views.basicRow
import com.pwillmann.moviediscovery.views.loadingRow
import jp.wasabeef.blurry.Blurry

private const val TAG = "BrowserFragment"

class DetailFragment : BaseMvRxFragment() {
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var recyclerView: EpoxyRecyclerView
    private lateinit var pullToRefreshLayout: SwipeRefreshLayout
    private lateinit var backgroundImageView: ImageView

    private lateinit var yearTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var genresTextView: TextView

    private val epoxyController by lazy { epoxyController() }
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
        return inflater.inflate(R.layout.detail_fragment_main, container, false).apply {
            recyclerView = findViewById(R.id.recycler_view)
            constraintLayout = findViewById(R.id.container)
            pullToRefreshLayout = findViewById(R.id.swiperefresh)
            backgroundImageView = findViewById(R.id.background)
            yearTextView = findViewById(R.id.year)
            nameTextView = findViewById(R.id.name)
            genresTextView = findViewById(R.id.genres)

            recyclerView.setController(epoxyController)
        }
    }

    override fun invalidate() {
        recyclerView.requestModelBuild()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var errorSnackbar: Snackbar? = null
        pullToRefreshLayout.setOnRefreshListener {
            viewModel.fetchTvShowData()
            viewModel.refreshSimilarTvShows()
            pullToRefreshLayout.isRefreshing = false
            if (errorSnackbar != null) {
                errorSnackbar!!.dismiss()
            }
        }

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
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        pullToRefreshLayout.setOnRefreshListener { }
        super.onDestroyView()
    }

    private fun setupBackgroundImage(tvShow: TvShow) {
        GlideApp.with(this)
                .asBitmap()
                .load("${TMDBBaseApiClient.tmdbImageBaseUrl}/${TMDBBaseApiClient.posterSizes[TMDBBaseApiClient.Companion.ImageSize.ORIGINAL.toString()]}/${tvShow.backdropPath}")
                .placeholder(R.color.black)
                .addListener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        Blurry.with(context)
                                .radius(10)
                                .sampling(8)
                                .color(ContextCompat.getColor(context!!, R.color.grey_transparent))
                                .async()
                                .animate(500)
                                .from(resource)
                                .into(backgroundImageView)
                        return true
                    }
                })
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
    }

    private fun epoxyController() = simpleController(viewModel) { state ->
        val tvShow = state.tvShow
        if (tvShow == null) {
            loadingRow {
                // Changing the ID will force it to rebind when new data is loaded even if it is
                // still on screen which will ensure that we trigger loading again.
                id("loading")
            }
            return@simpleController
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
            carouselModels.add(TvItemCompactModel_()
                    .id(similarShow.id)
                    .title(similarShow.name)
                    .rating(similarShow.voteAverage.toString())
                    .voteCount(similarShow.voteCount.toString())
                    .posterImageUrl("${TMDBBaseApiClient.tmdbImageBaseUrl}/${TMDBBaseApiClient.posterSizes[TMDBBaseApiClient.Companion.ImageSize.SMALL.toString()]}/${similarShow.posterPath}")
                    .clickListener { _ -> })
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