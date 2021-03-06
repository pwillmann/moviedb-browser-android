package com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow

import com.pwillmann.moviediscovery.lib.datasource.tmdb.model.PaginatedListResponse
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.model.TvShow
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.model.TvShowCompact
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitTvShowApi {

    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("language") query: String? = null,
        @Query("page") page: Int? = null
    ): Single<PaginatedListResponse<TvShowCompact>>

    @GET("tv/top_rated")
    fun getTopRatedTvShows(
        @Query("language") query: String? = null,
        @Query("page") page: Int? = null
    ): Single<PaginatedListResponse<TvShowCompact>>

    @GET("tv/{tv_id}/similar")
    fun getSimilarShows(
        @Path("tv_id") tvShowId: Long,
        @Query("language") query: String? = null,
        @Query("page") page: Int? = null
    ): Single<PaginatedListResponse<TvShowCompact>>

    @GET("tv/{tv_id}")
    fun getDetails(
        @Path("tv_id") tvShowId: Long,
        @Query("language") query: String? = null
    ): Single<TvShow>
}
