package com.pwillmann.moviediscovery.features.helloworld

import com.airbnb.mvrx.MvRxState
import com.pwillmann.moviediscovery.core.MvRxViewModel

data class HelloWorldState(val title: String = "Hello World") : MvRxState

class HelloWorldViewModel(initialState: HelloWorldState) : MvRxViewModel<HelloWorldState>(initialState)
