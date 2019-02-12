package com.pwillmann.moviediscovery.app.di.module

import com.pwillmann.moviediscovery.app.navigation.AppNavigator
import com.pwillmann.moviediscovery.feature.browser.BrowserNavigation
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NavigationModule {

    @JvmStatic
    @Provides
    @Singleton
    fun browserNavigation(appNavigator: AppNavigator): BrowserNavigation = appNavigator
}
