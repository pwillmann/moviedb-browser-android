package com.pwillmann.moviediscovery.app.di.component

import com.pwillmann.moviediscovery.app.MvRxApplication

interface AppComponent {
    fun inject(target: MvRxApplication)
}
