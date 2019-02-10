package com.pwillmann.moviediscovery.lib.datasource.tmdb

import com.pwillmann.moviediscovery.core.dagger.configuration.AppConfiguration
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.RetrofitTvShowApi
import com.pwillmann.moviediscovery.lib.datasource.tmdb.tvshow.mock.MockTvShowDataSource
import com.pwillmann.moviediscovery.lib.remote.RemoteModule
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object TMDBModule {

    @JvmStatic
    @Provides
    @Singleton
    fun tvShowApi(conf: AppConfiguration, retrofit: Retrofit, mockTvShowDataSource: MockTvShowDataSource): RetrofitTvShowApi {
        return when (conf.networkMode) {
            AppConfiguration.NetworkMode.LIVE -> retrofit.create(RetrofitTvShowApi::class.java)
            AppConfiguration.NetworkMode.MOCK -> mockTvShowDataSource
        }
    }

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
    @IntoSet
    @RemoteModule.App
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
