package com.santimattius.template.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
typealias AnimatedEnterTransition = AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?

@OptIn(ExperimentalAnimationApi::class)
typealias AnimatedExitTransition = AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?


fun NavHostController.navigatePoppingUpToStartDestination(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}


@ExperimentalAnimationApi
internal fun NavGraphBuilder.composable(
    navItem: NavCommand,
    enterTransition: AnimatedEnterTransition? = null,
    exitTransition: AnimatedExitTransition? = null,
    popEnterTransition: AnimatedEnterTransition? = enterTransition,
    popExitTransition: AnimatedExitTransition? = exitTransition,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = navItem.route,
        arguments = navItem.args,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) {
        content(it)
    }
}