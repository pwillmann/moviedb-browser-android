package com.pwillmann.moviediscovery.lib.datasource.tmdb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

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

inline fun <reified E> Moshi.paginatedListAdapter(elementType: Type = E::class.java): JsonAdapter<PaginatedListResponse<E>> {
    return adapter(paginatedListType<E>(elementType))
}

inline fun <reified E> paginatedListType(elementType: Type = E::class.java): Type {
    return Types.newParameterizedType(PaginatedListResponse::class.java, elementType)
}
