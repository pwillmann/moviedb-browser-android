package com.pwillmann.moviediscovery.app.di.component

import com.pwillmann.moviediscovery.app.ReleaseMvRxApplication
import com.pwillmann.moviediscovery.app.di.module.ActivityModule
import com.pwillmann.moviediscovery.app.di.module.ApplicationModule
import com.pwillmann.moviediscovery.app.di.module.FragmentModule
import com.pwillmann.moviediscovery.core.dagger.configuration.AppConfigurationModule
import com.pwillmann.moviediscovery.core.dagger.viewmodel.ViewModelBuilder
import com.pwillmann.moviediscovery.lib.datasource.tmdb.TMDBModule
import com.pwillmann.moviediscovery.lib.remote.RemoteModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
        modules = [
            ActivityModule::class,
            AndroidInjectionModule::class,
            AndroidSupportInjectionModule::class,
            AppConfigurationModule::class,
            ApplicationModule::class,
            FragmentModule::class,
            NavigationModule::class,
            RemoteModule::class,
            TMDBModule::class,
            ViewModelBuilder::class]
)
@Singleton
interface ReleaseAppComponent : AppComponent<ReleaseMvRxApplication>
