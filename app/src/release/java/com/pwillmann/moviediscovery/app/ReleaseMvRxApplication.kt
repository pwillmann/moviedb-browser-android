package com.pwillmann.moviediscovery.app

import com.pwillmann.moviediscovery.app.di.component.DaggerReleaseAppComponent
import com.pwillmann.moviediscovery.app.di.module.ApplicationModule

class ReleaseMvRxApplication : MvRxApplication() {

    override fun appDelegate(): AppDelegate = delegate

    override fun initDagger() = DaggerReleaseAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
            .inject(this)

    companion object {
        val delegate: AppDelegate = ReleaseAppDelegate()
    }
}
