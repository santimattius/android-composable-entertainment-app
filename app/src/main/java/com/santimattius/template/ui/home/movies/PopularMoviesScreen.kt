package com.santimattius.template.ui.home.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.ui.home.HomeScaffold

@ExperimentalLifecycleComposeApi
@Composable
fun PopularMoviesRoute(
    viewModel: PopularMoviesViewModel = hiltViewModel(),
    onMovieClick: (Movie) -> Unit,
    navigationUp: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScaffold(
        state = state,
        onMovieClick = onMovieClick,
        onRefresh = viewModel::refresh,
        navigationUp = navigationUp
    )
}