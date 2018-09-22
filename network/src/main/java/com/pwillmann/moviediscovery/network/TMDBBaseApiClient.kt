package com.pwillmann.moviediscovery.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

open class TMDBBaseApiClient {

    companion object {
        fun getClientBuilder(): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()
                    .addInterceptor(getApiKeyInterceptor(BuildConfig.TMDB_API_KEY))
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)

            return builder
        }

        fun getRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            return Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(client)
        }

        private fun getApiKeyInterceptor(apiKey: String): Interceptor {
            return Interceptor { chain ->

                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", apiKey)
                        .build()

                val reqBuilder = original.newBuilder()
                        .url(url)
                chain.proceed(reqBuilder.build())
            }
        }

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
}