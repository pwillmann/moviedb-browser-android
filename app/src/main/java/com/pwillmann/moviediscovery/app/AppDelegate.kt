package com.pwillmann.moviediscovery.app

import android.app.Application

// Used to be able to setup debug libraries in the debug build type, empty for release builds
abstract class AppDelegate {

    open fun onCreate(application: Application) {
    }
}
