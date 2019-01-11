package com.pwillmann.moviediscovery.model

import com.squareup.moshi.Json

data class Creator(
    val id: Long?,
    val name: String?,
    val gender: Long?,
    @Json(name = "credit_id") val creditID: String?,
    @Json(name = "profile_path") val profilePath: String?
)