package com.santimattius.template.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.santimattius.template.ui.components.content
import com.santimattius.template.ui.components.navigateToDeeplink

@ExperimentalLifecycleComposeApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content {
            HomeRoute(
                onMovieClick = {
                    val deepLink = "app://movie/${it.id}"
                    navigateToDeeplink(deepLink)
                },
                navigationUp = {
                    finish()
                }
            )
        }
    }
}