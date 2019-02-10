package com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.mock

import android.app.Application
import com.pwillmann.moviediscovery.lib.datasource.tmdb.model.PaginatedListResponse
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.RetrofitTvShowApi
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.model.TvShow
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.model.TvShowCompact
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Single
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockTvShowDataSource @Inject constructor(val application: Application, val moshi: Moshi) :
        RetrofitTvShowApi {
    override fun getPopularTvShows(query: String?, page: Int?): Single<PaginatedListResponse<TvShowCompact>> {
        return Single.just("tv_popular.json")
                .map { readAssetFileToString(it) }
                .map { moshi.paginatedListAdapter<TvShowCompact>(TvShowCompact::class.java).fromJson(it)!! }
    }

    override fun getSimilarShows(tvShowId: Long, query: String?, page: Int?): Single<PaginatedListResponse<TvShowCompact>> {
        return Single.just("tv_similar.json")
                .map { readAssetFileToString(it) }
                .map { moshi.paginatedListAdapter<TvShowCompact>(TvShowCompact::class.java).fromJson(it)!! }
    }

    override fun getDetails(tvShowId: Long, query: String?): Single<TvShow> {
        return Single.just("tv_details.json")
                .map { readAssetFileToString(it) }
                .map { moshi.adapter<TvShow>(TvShow::class.java).fromJson(it)!! }
    }

    private fun readAssetFileToString(fileName: String): String =
            application.assets.open(fileName).bufferedReader().use { it.readText() }

    inline fun <reified E> Moshi.paginatedListAdapter(elementType: Type = E::class.java): JsonAdapter<PaginatedListResponse<E>> {
        return adapter(paginatedListType<E>(elementType))
    }

    inline fun <reified E> paginatedListType(elementType: Type = E::class.java): Type {
        return Types.newParameterizedType(PaginatedListResponse::class.java, elementType)
    }
}
