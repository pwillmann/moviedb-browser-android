package com.pwillmann.moviediscovery.features.flow

import com.airbnb.mvrx.existingViewModel
import com.pwillmann.moviediscovery.core.BaseFragment
import com.pwillmann.moviediscovery.core.simpleController
import com.pwillmann.moviediscovery.views.marquee

class FlowCounterFragment : BaseFragment() {
    /**
     * Because we know that this Fragment isn't the first Fragment in the flow and that the ViewModel
     * will have been created by an earlier screen in the Fragment, we can use [existingViewModel]
     * which will throw an exception if it hasn't been created yet.
     *
     * This is useful if your ViewModel has dependencies for its initial state or anything else.
     */
    private val viewModel by existingViewModel(FlowViewModel::class)

    override fun epoxyController() = simpleController(viewModel) { state ->
        marquee {
            id("marquee")
            title("Counter: ${state.count}")
        }
    }
}