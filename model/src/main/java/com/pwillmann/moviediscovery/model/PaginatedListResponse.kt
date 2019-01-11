package com.pwillmann.moviediscovery.model

import com.squareup.moshi.Json

data class PaginatedListResponse<T>(
    val page: Int,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int,
    val results: List<T>
)

fun <T> PaginatedListResponse<T>?.mergeWith(newResponse: PaginatedListResponse<T>?): PaginatedListResponse<T>? {
    if (newResponse == null) return this
    if (this == null) return newResponse
    return this.copy(
            page = newResponse.page,
            totalResults = newResponse.totalResults,
            totalPages = newResponse.totalPages,
            results = this.results + newResponse.results
    )
}