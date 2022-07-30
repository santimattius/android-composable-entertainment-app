package com.santimattius.template.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.santimattius.template.R
import com.santimattius.template.ui.components.AppBar
import com.santimattius.template.ui.components.AppBarIcon
import com.santimattius.template.ui.navigation.Navigation
import com.santimattius.template.ui.theme.EntertainmentAppTheme

@ExperimentalAnimationApi
@ExperimentalLifecycleComposeApi
@Composable
fun EntertainmentApp(
    appState: AppState = rememberAppState(),
) {
    val upNavigation: @Composable () -> Unit = {
        AppBarIcon(
            imageVector = Icons.Default.ArrowBack,
            onClick = {
                appState.onUpClick()
            }
        )
    }

    EntertainmentScreen {
        Scaffold(
            topBar = {
                AppBar(
                    title = { Text(stringResource(id = R.string.title_app)) },
                    navigationIcon = if (appState.showUpNavigation) upNavigation else null
                )
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Navigation(navController = appState.navController)
                }
            }
        )
    }
}


@Composable
fun EntertainmentScreen(content: @Composable () -> Unit) {
    EntertainmentAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}