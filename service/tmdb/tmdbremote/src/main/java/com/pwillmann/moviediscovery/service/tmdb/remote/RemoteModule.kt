package com.pwillmann.moviediscovery.service.tmdb.remote

import android.annotation.SuppressLint
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object RemoteModule {
    @JvmStatic
    @Provides
    @Singleton
    fun okHttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    @JvmStatic
    @Provides
    @Singleton
    fun retrofitBuilder(moshi: Moshi): Retrofit.Builder = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))

    @JvmStatic
    @Provides
    @Singleton
    fun retrofit(builder: Retrofit.Builder, okHttpClient: OkHttpClient): Retrofit = builder
            .client(okHttpClient)
            .build()

    @JvmStatic
    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateJsonAdapter())
            .build()

//    @JvmStatic
//    @Provides
//    @Singleton
//    fun zenjobService(retrofit: Retrofit): TvShowsService =
//            retrofit.create(TvShowsService::class.java)

    @JvmStatic
    @Provides
    @Singleton
    fun tmdbApiInterceptor(): Interceptor =
            Interceptor { chain ->

                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                        .build()

                val reqBuilder = original.newBuilder()
                        .url(url)
                chain.proceed(reqBuilder.build())
            }
}

@SuppressLint("SimpleDateFormat")
class DateJsonAdapter {
    private val dateFormat: DateFormat

    init {
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
    }

    @ToJson
    @Synchronized
    internal fun dateToJson(d: Date): String {
        return dateFormat.format(d)
    }

    @FromJson
    @Synchronized
    @Throws(ParseException::class)
    internal fun dateToJson(s: String): Date {
        return dateFormat.parse(s)
    }
}