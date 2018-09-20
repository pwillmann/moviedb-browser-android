package com.pwillmann.moviediscovery.features.flow

import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.pwillmann.moviediscovery.core.BaseFragment
import com.pwillmann.moviediscovery.core.simpleController
import com.pwillmann.moviediscovery.views.basicRow
import com.pwillmann.moviediscovery.views.marquee

class FlowIntroFragment : BaseFragment() {

    private val viewModel by activityViewModel(FlowViewModel::class)

    override fun epoxyController() = simpleController {
        marquee {
            id("marquee")
            title("Intro")
            subtitle("Set the initial counter value")
        }

        arrayOf(0, 10, 50, 100, 1_000, 10_000).forEach {
            basicRow {
                id(it)
                title("$it")
                clickListener { _ ->
                    viewModel.setCount(it)
                    findNavController().navigate(R.id.action_flowIntroFragment_to_flowCounterFragment)
                }
            }
        }
    }
}