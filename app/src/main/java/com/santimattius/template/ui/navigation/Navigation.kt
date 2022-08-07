package com.santimattius.template.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
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
                width = constraints.maxWidth.div(2),
            )
            tvShowNav(
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
            navItem = NavCommand.ContentType(Feature.TV_SHOWS),
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
            navItem = NavCommand.ContentTypeDetail(Feature.TV_SHOWS),
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