package dev.rohitverma882.quotee

import android.app.Application
import android.content.pm.ApplicationInfo

import dagger.hilt.android.HiltAndroidApp

import timber.log.Timber

@HiltAndroidApp
class QuoteeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (applicationInfo.isDebuggable) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

val ApplicationInfo.isDebuggable get() = (flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0