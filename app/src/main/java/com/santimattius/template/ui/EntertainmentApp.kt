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
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.santimattius.template.R
import com.santimattius.template.ui.components.AppBar
import com.santimattius.template.ui.components.AppBarIcon
import com.santimattius.template.ui.components.AppBottomNavigation
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
                if (appState.showTopAppBar) {
                    AppBar(
                        title = { Text(stringResource(id = R.string.title_app)) },
                        navigationIcon = if (appState.showUpNavigation) upNavigation else null
                    )
                }
            },
            bottomBar = {
                if (appState.showBottomNavigation) {
                    AppBottomNavigation(
                        bottomNavOptions = AppState.BOTTOM_NAV_ITEMS,
                        currentRoute = appState.currentRoute,
                        onNavItemClick = { appState.onNavItemClick(it) })
                }
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Navigation(navController = appState.navController)
                }
            }
        )
        SetStatusBarColorEffect(
            color = if (appState.isFullScreen) {
                Color.Transparent
            } else {
                MaterialTheme.colors.primaryVariant
            }
        )
    }
}

@Composable
fun SetStatusBarColorEffect(
    color: Color = MaterialTheme.colors.primaryVariant,
) {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = color == Color.Transparent
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