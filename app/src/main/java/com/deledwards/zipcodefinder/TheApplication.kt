package com.deledwards.zipcodefinder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}