package com.pwillmann.moviediscovery.models

import com.squareup.moshi.Json

data class TvShow(
    val popularity: Double,
    val id: Long,
    val overview: String,
    val name: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "first_air_date") val firstAirDate: String,
    @Json(name = "origin_country") val originCountry: List<String>,
    @Json(name = "genre_ids") val genreIDS: List<Long>,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "vote_count") val voteCount: Long,
    @Json(name = "original_name") val originalName: String
)