package com.santimattius.template.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

/**
 * start or load koin
 */
fun initKoin(app: Application, init: KoinApplicationWrapper.() -> Unit) {
    val wrapper = KoinApplicationWrapper(app)
    wrapper.init()
    with(wrapper) {
        val koinContext = GlobalContext.getOrNull()
        if (koinContext == null) {
            startKoin {
                androidLogger(level)
                androidContext(application)
                modules(modules)
            }
        } else {
            loadKoinModules(modules)
        }
    }
}

class KoinApplicationWrapper internal constructor(
    val application: Application,
    var modules: MutableList<Module> = mutableListOf(),
    var level: Level = Level.NONE,
) {

    fun module(module: Module) {
        modules.add(module)
    }

    fun level(level: Level) {
        this.level = level
    }

    fun modules(module: List<Module>) {
        modules.addAll(module)
    }
}