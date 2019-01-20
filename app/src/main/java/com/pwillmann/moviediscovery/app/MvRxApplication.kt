package com.pwillmann.moviediscovery.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.pwillmann.moviediscovery.app.di.component.DaggerAppComponent
import com.pwillmann.moviediscovery.app.di.module.ApplicationModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MvRxApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
                .inject(this)
    }
}