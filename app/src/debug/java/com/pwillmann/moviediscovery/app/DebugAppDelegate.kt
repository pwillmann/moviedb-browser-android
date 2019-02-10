package com.pwillmann.moviediscovery.app

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.RecordLeakService
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

// Used to be able to setup debug libraries in the debug build type, empty for release builds
@Singleton
class DebugAppDelegate @Inject constructor(
    private val networkFlipperPlugin: NetworkFlipperPlugin
) : AppDelegate() {

    override fun onCreate(application: Application) {
        SoLoader.init(application, false)
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            val refWatcher: RefWatcher = LeakCanary.refWatcher(application)
                    .listenerServiceClass(RecordLeakService::class.java)
                    .buildAndInstall()

            if (FlipperUtils.shouldEnableFlipper(application)) {
                val client = AndroidFlipperClient.getInstance(application)
                client.addPlugin(InspectorFlipperPlugin(application, DescriptorMapping.withDefaults()))
                client.addPlugin(CrashReporterPlugin.getInstance())
                client.addPlugin(LeakCanaryFlipperPlugin())
                client.addPlugin(networkFlipperPlugin)
                client.start()
            }
        }
    }
}
