package com.santimattius.template.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.santimattius.template.EntertainmentApplication
import com.santimattius.template.ui.components.openLink

@ExperimentalLifecycleComposeApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content {
            HomeRoute(
                onMovieClick = {
                    openLink(it.imageUrl)
                },
                onBack = {
                    finish()
                }
            )
        }
    }
}

fun ComponentActivity.content(
    parent: CompositionContext? = null,
    content: @Composable () -> Unit,
) {
    setContent(parent) {
        EntertainmentApplication {
            content()
        }
    }
}