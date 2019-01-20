package com.pwillmann.moviediscovery.feature.detail

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@AssistedModule
@Module(includes = [AssistedInject_DetailFragmentBuilder::class])
abstract class DetailFragmentBuilder {

    @ContributesAndroidInjector
    internal abstract fun contributeDetailFragment(): DetailFragment
}