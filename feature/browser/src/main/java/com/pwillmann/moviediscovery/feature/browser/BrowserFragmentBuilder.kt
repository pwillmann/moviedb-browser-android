package com.pwillmann.moviediscovery.feature.browser

import androidx.lifecycle.ViewModel
import com.pwillmann.moviediscovery.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class BrowserFragmentBuilder {
    @ContributesAndroidInjector
    internal abstract fun contributeBrowserFragment(): BrowserFragment

    @Binds
    @IntoMap
    @ViewModelKey(BrowserViewModel::class)
    abstract fun browserViewModel(viewModel: BrowserViewModel): ViewModel
}