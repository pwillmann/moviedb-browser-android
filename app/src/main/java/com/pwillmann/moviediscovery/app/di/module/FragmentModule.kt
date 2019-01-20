package com.pwillmann.moviediscovery.app.di.module

import com.pwillmann.moviediscovery.feature.browser.BrowserFragmentBuilder
import com.pwillmann.moviediscovery.feature.detail.DetailFragmentBuilder
import dagger.Module

@Module(includes = [
    BrowserFragmentBuilder::class,
    DetailFragmentBuilder::class
])
abstract class FragmentModule