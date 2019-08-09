package com.chethan.babylon

import android.app.Activity
import android.app.Application
import com.chethan.babylon.di.AppInjector
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Chethan on 7/30/2019.
 */

class MainApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this);
        }
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
