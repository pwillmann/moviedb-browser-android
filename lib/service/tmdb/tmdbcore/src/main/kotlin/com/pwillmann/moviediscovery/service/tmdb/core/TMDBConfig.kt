package com.pwillmann.moviediscovery.service.tmdb.core

object TMDBConfig {

    enum class ImageSize {
        THUMB, SMALL, MEDIUM, LARGE, HD, ORIGINAL
    }

    val tmdbImageBaseUrl = "https://image.tmdb.org/t/p"

    val backdropSizes = mapOf(
            ImageSize.THUMB.toString() to "w300",
            ImageSize.SMALL.toString() to "w300",
            ImageSize.MEDIUM.toString() to "w300",
            ImageSize.LARGE.toString() to "w780",
            ImageSize.HD.toString() to "w1280",
            ImageSize.ORIGINAL.toString() to "original"
    )
    val posterSizes = mapOf(
            ImageSize.THUMB.toString() to "w92",
            "w154" to "w154",
            ImageSize.SMALL.toString() to "w185",
            ImageSize.MEDIUM.toString() to "w342",
            "w500" to "w500",
            ImageSize.LARGE.toString() to "w780",
            ImageSize.HD.toString() to "w780",
            ImageSize.ORIGINAL.toString() to "original"
    )
}
