package com.pwillmann.moviediscovery.app

import android.os.Bundle
import androidx.navigation.findNavController
import com.airbnb.mvrx.BaseMvRxActivity
import com.pwillmann.moviediscovery.app.navigation.AppNavigator
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

/**
 * Extend this class to get MvRx support out of the box.
 *
 * The purpose of this class is to:
 * 1) Be the host of MvRxFragments. MvRxFragments are the screen unit in MvRx. Activities are meant
 *    to just be the shell for your Fragments. There should be no business logic in your
 *    Activities anymore. Use activityViewModel to share state between screens.
 * 2) Properly configure MvRx so it has things like the correct ViewModelStore.
 *
 * To integrate this into your app. you may:
 * 1) Extend this directly.
 * 2) Replace your BaseActivity super class with this one.
 * 3) Manually integrate this into your base Activity (not recommended).
 */
class MainActivity : BaseMvRxActivity() {
    @Inject
    lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logLifecycle("onCreate()")
        appNavigator.bind(findNavController(R.id.my_nav_host_fragment))
    }

    override fun onStart() {
        super.onStart()
        logLifecycle("onStart()")
    }

    override fun onResume() {
        super.onResume()
        logLifecycle("onResume()")
    }

    override fun onPause() {
        super.onPause()
        logLifecycle("onPause()")
    }

    override fun onStop() {
        super.onStop()
        logLifecycle("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        logLifecycle("onDestroy()")
        appNavigator.unbind()
    }

    private fun logLifecycle(message: String) {
        Timber.tag("Lifecycle")
        Timber.w("%s:: $message", javaClass.simpleName)
    }
}
