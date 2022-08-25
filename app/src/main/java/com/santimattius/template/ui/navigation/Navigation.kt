package com.santimattius.template.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.santimattius.template.ui.detail.movie.MovieDetailRoute
import com.santimattius.template.ui.detail.tvshow.TvShowDetailRoute
import com.santimattius.template.ui.home.movies.PopularMoviesRoute
import com.santimattius.template.ui.home.tvshows.PopularTvShowsRoute

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
                width = constraints.maxWidth,
            )
            tvShowNav(
                navController = navController,
                width = constraints.maxWidth,
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
            navCommand = NavCommand.ContentType(Feature.MOVIES),
        ) {
            PopularMoviesRoute(
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
            navCommand = NavCommand.ContentTypeDetail(Feature.MOVIES),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { width }
                ) + fadeIn(animationSpec = tween(1000))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { width }
                ) + fadeOut(animationSpec = tween(1000))
            }
        ) {
            MovieDetailRoute()
        }
    }
}

@ExperimentalLifecycleComposeApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.tvShowNav(
    navController: NavController,
    width: Int,
) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.TV_SHOWS).route,
        route = Feature.TV_SHOWS.route
    ) {
        composable(
            navCommand = NavCommand.ContentType(Feature.TV_SHOWS),
        ) {
            PopularTvShowsRoute(
                onTvShowClick = { tvShow ->
                    navController.navigate(NavCommand.ContentTypeDetail(Feature.TV_SHOWS)
                        .createRoute(tvShow.id))
                },
                navigationUp = {
                    navController.popBackStack()
                },
            )
        }
        composable(
            navCommand = NavCommand.ContentTypeDetail(Feature.TV_SHOWS),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { width }
                )
            }
        ) {
            TvShowDetailRoute()
        }
    }
}