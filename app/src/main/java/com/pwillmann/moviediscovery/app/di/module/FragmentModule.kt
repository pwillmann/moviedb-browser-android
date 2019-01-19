package com.pwillmann.moviediscovery.app.di.module

import com.pwillmann.moviediscovery.feature.browser.BrowserFragment
import com.pwillmann.moviediscovery.feature.browser.BrowserModule
import com.pwillmann.moviediscovery.app.di.ViewModelFactoryModule
import com.pwillmann.moviediscovery.feature.detail.DetailFragment
import com.pwillmann.moviediscovery.feature.detail.DetailModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector(modules = [BrowserModule::class, ViewModelFactoryModule::class])
    internal abstract fun contributeBrowserFragment(): BrowserFragment

    @ContributesAndroidInjector(modules = [DetailModule::class, ViewModelFactoryModule::class])
    internal abstract fun contributeDetailFragment(): DetailFragment
}