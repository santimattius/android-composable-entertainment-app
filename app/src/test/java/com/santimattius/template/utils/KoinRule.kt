package com.santimattius.template.utils

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.santimattius.template.di.initKoin
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.robolectric.RuntimeEnvironment


class KoinRule(
    private val context: Context,
    private val modules: List<Module>,
) : TestWatcher() {

    constructor(context: Context, module: Module) : this(context, mutableListOf(module))

    override fun starting(description: Description?) {
        super.starting(description)
        initKoin(context.applicationContext as Application) {
            modules(this@KoinRule.modules)
        }
    }

    override fun finished(description: Description?) {
        super.finished(description)
        stopKoin()
    }

    companion object {

        fun robolectric(module: Module) = robolectric(mutableListOf(module))

        private fun robolectric(modules: List<Module> = emptyList()) =
            KoinRule(RuntimeEnvironment.getApplication(), modules)

        fun androidx(module: Module) = androidx(mutableListOf(module))

        fun androidx(modules: List<Module> = emptyList()) =
            KoinRule(ApplicationProvider.getApplicationContext(), modules)
    }
}