package com.pwillmann.moviediscovery.service.remote

import com.pwillmann.moviediscovery.model.PaginatedListResponse
import com.pwillmann.moviediscovery.model.TvShow
import com.pwillmann.moviediscovery.model.TvShowCompact
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsService {

    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("language") query: String? = null,
        @Query("page") page: Int? = null
    ): Observable<PaginatedListResponse<TvShowCompact>>

    @GET("tv/{tv_id}/similar")
    fun getSimilarShows(
        @Path("tv_id") tvShowId: Long,
        @Query("language") query: String? = null,
        @Query("page") page: Int? = null
    ): Observable<PaginatedListResponse<TvShowCompact>>

    @GET("tv/{tv_id}")
    fun getDetails(
        @Path("tv_id") tvShowId: Long,
        @Query("language") query: String? = null
    ): Observable<TvShow>
}