package com.pwillmann.moviediscovery.lib.datasource.tmdb

object TMDBConfig {

    enum class ImageSize {
        THUMB, SMALL, MEDIUM, LARGE, HD, ORIGINAL
    }

    val tmdbImageBaseUrl = "https://image.tmdb.org/t/p"

    val backdropSizes = mapOf(
            TMDBConfig.ImageSize.THUMB.toString() to "w300",
            TMDBConfig.ImageSize.SMALL.toString() to "w300",
            TMDBConfig.ImageSize.MEDIUM.toString() to "w300",
            TMDBConfig.ImageSize.LARGE.toString() to "w780",
            TMDBConfig.ImageSize.HD.toString() to "w1280",
            TMDBConfig.ImageSize.ORIGINAL.toString() to "original"
    )
    val posterSizes = mapOf(
            TMDBConfig.ImageSize.THUMB.toString() to "w92",
            "w154" to "w154",
            TMDBConfig.ImageSize.SMALL.toString() to "w185",
            TMDBConfig.ImageSize.MEDIUM.toString() to "w342",
            "w500" to "w500",
            TMDBConfig.ImageSize.LARGE.toString() to "w780",
            TMDBConfig.ImageSize.HD.toString() to "w780",
            TMDBConfig.ImageSize.ORIGINAL.toString() to "original"
    )
}
