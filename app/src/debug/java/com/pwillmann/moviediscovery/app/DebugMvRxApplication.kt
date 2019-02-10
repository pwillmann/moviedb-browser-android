package com.pwillmann.moviediscovery.app

import com.pwillmann.moviediscovery.app.di.component.DaggerDebugAppComponent
import com.pwillmann.moviediscovery.app.di.module.ApplicationModule
import javax.inject.Inject

class DebugMvRxApplication : MvRxApplication() {
    @Inject
    lateinit var appDelegate: DebugAppDelegate

    override fun appDelegate(): AppDelegate = appDelegate

    override fun onCreate() {
        DaggerDebugAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
                .inject(this)
        super.onCreate()
    }
}
