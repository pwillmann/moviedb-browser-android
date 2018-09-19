package com.pwillmann.moviediscovery.network

import com.pwillmann.moviediscovery.models.PaginatedListResponse
import com.pwillmann.moviediscovery.models.TvShow
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularTvShowsService {

    @GET("/tv/popular")
    fun getPopularTvShows(
        @Query("language") query: String? = null,
        @Query("page") page: Int? = null
    ): Observable<PaginatedListResponse<TvShow>>
}