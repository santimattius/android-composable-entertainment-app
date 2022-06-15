package com.santimattius.template.di

import android.app.Application
import org.koin.core.logger.Level

class AppConfiguration private constructor() {

    companion object {
        fun init(app: Application) {
            initKoin(app) {
                level(Level.NONE)
                modules(moduleDefinitions)
            }
        }
    }
}