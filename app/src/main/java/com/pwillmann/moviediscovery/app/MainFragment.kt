package com.pwillmann.moviediscovery.app

import com.pwillmann.moviediscovery.core.BaseFragment
import com.pwillmann.moviediscovery.core.simpleController
import com.pwillmann.moviediscovery.views.basicRow
import com.pwillmann.moviediscovery.views.marquee

class MainFragment : BaseFragment() {

    override fun epoxyController() = simpleController {
        marquee {
            id("marquee")
            title("Welcome to MvRx")
            subtitle("Select a demo below")
        }

        basicRow {
            id("hello_world_epoxy")
            title("Hello World (Epoxy)")
            subtitle(demonstrates("Simple MvRx usage", "Epoxy integration"))
            clickListener { _ -> navigateTo(R.id.action_main_to_helloWorldEpoxyFragment) }
        }

        basicRow {
            id("dad_jokes")
            title("Dad Jokes")
            subtitle(
                    demonstrates(
                            "fragmentViewModel",
                            "Fragment arguments",
                            "Network requests",
                            "Pagination",
                            "Dependency Injection"
                    )
            )
            clickListener { _ -> navigateTo(R.id.action_mainFragment_to_dadJokeIndex) }
        }

        basicRow {
            id("flow")
            title("Flow")
            subtitle(
                    demonstrates(
                            "Sharing data across screens",
                            "activityViewModel and existingViewModel"
                    )
            )
            clickListener { _ -> navigateTo(R.id.action_main_to_flowIntroFragment) }
        }
        basicRow {
            id("browser")
            title("Browser")
            subtitle(
                    demonstrates(
                            "Browse tv shows"
                    )
            )
            clickListener { _ -> navigateTo(R.id.action_main_to_browserFragment) }
        }
    }

    private fun demonstrates(vararg items: String) =
            arrayOf("Demonstrates:", *items).joinToString("\n\t\t• ")
}