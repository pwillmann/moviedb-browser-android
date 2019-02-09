package com.pwillmann.moviediscovery.app.di.component

import com.pwillmann.moviediscovery.app.MvRxApplication
import com.pwillmann.moviediscovery.app.di.module.ActivityModule
import com.pwillmann.moviediscovery.app.di.module.ApplicationModule
import com.pwillmann.moviediscovery.app.di.module.FragmentModule
import com.pwillmann.moviediscovery.core.dagger.viewmodel.ViewModelBuilder
import com.pwillmann.moviediscovery.service.tmdb.mock.MockModule
import com.pwillmann.moviediscovery.service.tmdb.remote.RemoteModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
        modules = [
            ActivityModule::class,
            AndroidInjectionModule::class,
            AndroidSupportInjectionModule::class,
            ApplicationModule::class,
            FragmentModule::class,
            MockModule::class,
            RemoteModule::class,
            ViewModelBuilder::class]
)
@Singleton
interface AppComponent {
    fun inject(target: MvRxApplication)
}
