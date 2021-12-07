package com.deledwards.zipcodefinder

import android.R
import android.app.Application
import android.content.res.Resources
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import java.io.InputStream

@HiltAndroidApp
class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}