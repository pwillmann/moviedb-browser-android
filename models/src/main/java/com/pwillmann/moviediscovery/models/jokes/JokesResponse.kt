package com.pwillmann.moviediscovery.models.jokes

import com.squareup.moshi.Json

data class JokesResponse(
    @Json(name = "next_page") val nextPage: Int,
    @Json(name = "results") val results: List<Joke>
)