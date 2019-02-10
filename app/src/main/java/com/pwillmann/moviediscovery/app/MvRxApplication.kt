package com.pwillmann.moviediscovery.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.RecordLeakService
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.pwillmann.moviediscovery.app.di.component.DaggerAppComponent
import com.pwillmann.moviediscovery.app.di.module.ApplicationModule
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import timber.log.Timber.DebugTree
import timber.log.Timber

class MvRxApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var networkFlipperPlugin: NetworkFlipperPlugin

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
                .inject(this)

        setupTools()
    }

    private fun setupTools() {
        SoLoader.init(this, false)
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())

            val refWatcher: RefWatcher = LeakCanary.refWatcher(this)
                    .listenerServiceClass(RecordLeakService::class.java)
                    .buildAndInstall()

            if (FlipperUtils.shouldEnableFlipper(this)) {
                val client = AndroidFlipperClient.getInstance(this)
                client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
                client.addPlugin(CrashReporterPlugin.getInstance())
                client.addPlugin(LeakCanaryFlipperPlugin())
                client.addPlugin(networkFlipperPlugin)
                client.start()
            }
        }
    }
}
