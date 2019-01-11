package com.pwillmann.moviediscovery.model

import com.squareup.moshi.Json
import java.util.Date

data class TvShow(
    val id: Long?,
    val name: String?,
    val genres: List<Genre>?,
    val languages: List<String>?,
    val homepage: String?,
    val overview: String?,
    val popularity: Double?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "created_by") val creators: List<Creator>?,
    @Json(name = "episode_run_time") val episodeRunTime: List<Long>?,
    @Json(name = "first_air_date") val firstAirDate: Date?,
    @Json(name = "number_of_episodes") val numberOfEpisodes: Long?,
    @Json(name = "number_of_seasons") val numberOfSeasons: Long?,
    @Json(name = "origin_country") val originCountry: List<String>?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_name") val originalName: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Long?
)