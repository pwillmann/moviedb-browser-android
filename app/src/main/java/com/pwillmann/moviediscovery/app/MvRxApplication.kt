package com.pwillmann.moviediscovery.app

import android.app.Application
import com.pwillmann.moviediscovery.network.TvShowsService
import com.pwillmann.moviediscovery.network.TMDBBaseApiClient
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

class MvRxApplication : Application() {
    private val tvShowsServiceModule: Module = applicationContext {
        bean {
            return@bean TMDBBaseApiClient.getRetrofitBuilder(TMDBBaseApiClient.getClientBuilder().build())
                    .build().create(TvShowsService::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(tvShowsServiceModule))
    }
}