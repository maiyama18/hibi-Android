package com.muijp.hibi

import android.app.Application
import timber.log.Timber

class HibiApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Timber.d("timber planted")
    }
}