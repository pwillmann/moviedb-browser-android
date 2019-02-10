package com.pwillmann.moviediscovery.core.dagger.configuration

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppConfigurationModule {

    @JvmStatic
    @Provides
    @Singleton
    fun appConfiguration(): AppConfiguration = AppConfiguration()
}
