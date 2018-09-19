package com.pwillmann.moviediscovery.features.helloworld

import com.airbnb.mvrx.fragmentViewModel
import com.pwillmann.moviediscovery.core.BaseFragment
import com.pwillmann.moviediscovery.core.simpleController
import com.pwillmann.moviediscovery.views.marquee

class HelloWorldEpoxyFragment : BaseFragment() {
    private val viewModel: HelloWorldViewModel by fragmentViewModel()

    override fun epoxyController() = simpleController(viewModel) { state ->
        marquee {
            id("marquee")
            title(state.title)
        }
    }
}
