package com.santimattius.template.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.santimattius.template.ui.detail.MovieDetailRoute
import com.santimattius.template.ui.home.HomeRoute

@ExperimentalLifecycleComposeApi
@ExperimentalAnimationApi
@Composable
fun Navigation(
    navController: NavHostController = rememberAnimatedNavController(),
) {
    BoxWithConstraints {
        AnimatedNavHost(
            navController = navController,
            startDestination = Feature.MOVIES.route
        ) {
            movieNav(
                navController = navController,
                width = constraints.maxWidth.div(2),
            )
        }
    }
}

@ExperimentalLifecycleComposeApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.movieNav(
    navController: NavController,
    width: Int,
) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.MOVIES).route,
        route = Feature.MOVIES.route
    ) {
        composable(
            navItem = NavCommand.ContentType(Feature.MOVIES),
        ) {
            HomeRoute(
                onMovieClick = { movie ->
                    navController.navigate(NavCommand.ContentTypeDetail(Feature.MOVIES)
                        .createRoute(movie.id))
                },
                navigationUp = {
                    navController.popBackStack()
                },
            )
        }
        composable(
            navItem = NavCommand.ContentTypeDetail(Feature.MOVIES),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { width }
                )
            }
        ) {
            MovieDetailRoute()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
typealias AnimatedEnterTransition = AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?

@OptIn(ExperimentalAnimationApi::class)
typealias AnimatedExitTransition = AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?

@ExperimentalAnimationApi
private fun NavGraphBuilder.composable(
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