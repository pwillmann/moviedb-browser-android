package com.pwillmann.moviediscovery.core.dagger.configuration

class AppConfiguration(var networkMode: NetworkMode = NetworkMode.LIVE) {

    enum class NetworkMode {
        LIVE, MOCK
    }
}
