package com.pwillmann.moviediscovery.app.di.component

import com.pwillmann.moviediscovery.app.MvRxApplication

interface AppComponent<T : MvRxApplication> {
    fun inject(target: T)
}
