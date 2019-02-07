package com.pwillmann.moviediscovery.core.mvrx

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.pwillmann.moviediscovery.core.KotterKnife
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber

abstract class MvRxEpoxyFragment : BaseMvRxFragment() {

    protected val epoxyController by lazy { epoxyController() }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logLifecycle("onViewCreated()")
        recyclerView().setController(epoxyController)
    }

    @CallSuper
    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
        logLifecycle("onDestroyView()")
        KotterKnife.reset(this)
    }

    @CallSuper
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        logLifecycle("onAttach()")
    }

    @CallSuper
    override fun invalidate() {
        recyclerView().requestModelBuild()
        logLifecycle("invalidate()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logLifecycle("onCreate()")
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
    }

    abstract fun epoxyController(): MvRxEpoxyController
    abstract fun recyclerView(): EpoxyRecyclerView

    private fun logLifecycle(message: String) {
        Timber.tag("Lifecycle")
        Timber.w("%s:: $message", javaClass.simpleName)
    }
}
