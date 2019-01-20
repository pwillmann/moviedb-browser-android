package com.pwillmann.moviediscovery.app.di.module

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val app: Application) {
    @Provides
    fun appContext(): Application = app
}