package com.pwillmann.moviediscovery.lib.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object RemoteModule {

    @Qualifier
    annotation class Network

    @Qualifier
    annotation class App

    @JvmStatic
    @Provides
    @ElementsIntoSet
    @Network
    fun networkInterceptors(): Set<@JvmSuppressWildcards Interceptor> {
        return HashSet()
    }

    @JvmStatic
    @Provides
    @ElementsIntoSet
    @App
    fun appInterceptors(): Set<@JvmSuppressWildcards Interceptor> {
        return HashSet()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun okHttpClient(
        @Network networkInterceptors: Set<@JvmSuppressWildcards Interceptor>,
        @App appInterceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in appInterceptors) {
            builder.addInterceptor(interceptor)
        }
        for (interceptor in networkInterceptors) {
            builder.addNetworkInterceptor(interceptor)
        }
        return builder.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateJsonAdapter())
            .build()
}
