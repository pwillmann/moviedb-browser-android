package com.pwillmann.moviediscovery.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.pwillmann.moviediscovery.app.di.component.DaggerAppComponent
import com.pwillmann.moviediscovery.app.di.module.ApplicationModule
import com.pwillmann.moviediscovery.service.remote.TMDBBaseApiClient
import com.pwillmann.moviediscovery.service.remote.TvShowsService
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import javax.inject.Inject

class MvRxApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    private val tvShowsServiceModule: Module = applicationContext {
        bean {
            return@bean TMDBBaseApiClient.getRetrofitBuilder(TMDBBaseApiClient.getClientBuilder().build())
                    .build().create(TvShowsService::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(tvShowsServiceModule))
        DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
                .inject(this)
    }
}