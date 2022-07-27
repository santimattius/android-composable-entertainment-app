package com.santimattius.template.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.santimattius.template.R
import com.santimattius.template.ui.components.DialogAction
import com.santimattius.template.ui.home.components.Center
import com.santimattius.template.ui.home.components.ErrorDialog
import com.santimattius.template.ui.home.components.MovieView
import com.santimattius.template.ui.home.models.HomeState
import com.santimattius.template.ui.home.models.MovieUiModel
import org.koin.androidx.compose.getViewModel

@ExperimentalLifecycleComposeApi
@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = getViewModel(),
    onMovieClick: (MovieUiModel) -> Unit,
    navigationUp: () -> Unit,
) {
    val state by homeViewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onMovieClick = onMovieClick,
        onRefresh = homeViewModel::refresh,
        navigationUp = navigationUp
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onMovieClick: (MovieUiModel) -> Unit,
    onRefresh: () -> Unit,
    navigationUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        }
    ) {
        HomeContent(
            state = state,
            onMovieClick = onMovieClick,
            onRefresh = onRefresh,
            navigationUp = navigationUp
        )
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    onMovieClick: (MovieUiModel) -> Unit,
    onRefresh: () -> Unit,
    navigationUp: () -> Unit,
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh,
    ) {
        when {
            state.hasError -> {
                ErrorDialog(
                    message = stringResource(
                        id = R.string.message_loading_error
                    ),
                    positiveAction = DialogAction(text = stringResource(R.string.button_text_positive_error)) {
                        onRefresh()
                    },
                    negativeAction = DialogAction(text = stringResource(R.string.button_text_negative_error)) {
                        navigationUp()
                    }
                )
            }
            state.isLoading -> {
                Center {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            else -> {
                if (state.data.isEmpty()) {
                    Center {
                        Text(text = stringResource(id = R.string.message_text_empty_result))
                    }
                } else {
                    MovieGridView(
                        movies = state.data,
                        onMovieClick = onMovieClick
                    )
                }

            }
        }
    }
}

@Composable
fun MovieGridView(
    movies: List<MovieUiModel>,
    onMovieClick: (MovieUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(R.dimen.item_min_width)),
        contentPadding = PaddingValues(dimensionResource(R.dimen.x_small)),
        modifier = modifier
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieView(
                movie = movie,
                modifier = Modifier.clickable { onMovieClick(movie) }
            )
        }
    }
}

