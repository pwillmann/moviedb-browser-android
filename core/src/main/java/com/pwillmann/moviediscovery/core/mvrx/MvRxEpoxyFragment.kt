package com.pwillmann.moviediscovery.core.mvrx

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.pwillmann.moviediscovery.core.KotterKnife
import dagger.android.support.AndroidSupportInjection

abstract class MvRxEpoxyFragment : BaseMvRxFragment() {

    protected val epoxyController by lazy { epoxyController() }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView().setController(epoxyController)
    }

    @CallSuper
    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
        KotterKnife.reset(this)
    }

    @CallSuper
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
//        Timber.w("Lifecycle onAttach %s", javaClass.simpleName)
        super.onAttach(context)
    }

    @CallSuper
    override fun invalidate() {
        recyclerView().requestModelBuild()
    }

    abstract fun epoxyController(): MvRxEpoxyController
    abstract fun recyclerView(): EpoxyRecyclerView
}