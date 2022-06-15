package com.santimattius.template

import android.app.Application
import com.santimattius.template.di.AppConfiguration

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppConfiguration.init(app = this)
    }
}