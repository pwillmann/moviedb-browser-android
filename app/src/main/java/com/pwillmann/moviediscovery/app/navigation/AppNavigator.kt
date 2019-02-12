package com.pwillmann.moviediscovery.app.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.airbnb.mvrx.MvRx
import com.pwillmann.moviediscovery.app.R
import com.pwillmann.moviediscovery.feature.browser.BrowserNavigation
import com.pwillmann.moviediscovery.feature.detail.DetailStateArgs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigator @Inject constructor() : BrowserNavigation {

    private var navController: NavController? = null

    override fun navigateToDetail(tvShowId: Long) {
        val args = DetailStateArgs(tvShowId)
        val bundle = args.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        navController?.navigate(R.id.detail_nav_graph, bundle)
    }

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        navController = null
    }
}
