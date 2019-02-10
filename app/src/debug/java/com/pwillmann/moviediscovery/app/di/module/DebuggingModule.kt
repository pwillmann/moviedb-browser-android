package com.pwillmann.moviediscovery.app.di.module

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.pwillmann.moviediscovery.lib.remote.RemoteModule
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
object DebuggingModule {

    @JvmStatic
    @Provides
    @Singleton
    fun networkFlipperPlugin(): NetworkFlipperPlugin = NetworkFlipperPlugin()

    @JvmStatic
    @Provides
    @Singleton
    @IntoSet
    @RemoteModule.Network
    fun tmdbApiInterceptor(networkFlipperPlugin: NetworkFlipperPlugin): Interceptor = FlipperOkhttpInterceptor(networkFlipperPlugin)
}
