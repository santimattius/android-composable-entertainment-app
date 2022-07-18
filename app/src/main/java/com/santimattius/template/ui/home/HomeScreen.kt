package com.santimattius.template.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.santimattius.template.R
import com.santimattius.template.ui.components.DialogAction
import com.santimattius.template.ui.home.components.MovieView
import com.santimattius.template.ui.home.models.HomeState
import com.santimattius.template.ui.home.models.MovieUiModel

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    onMovieClick: (MovieUiModel) -> Unit,
    onBack: () -> Unit,
) {
    val state by homeViewModel.state.observeAsState(HomeState.Loading)

    HomeScreen(
        state = state,
        onMovieClick = onMovieClick,
        onRefresh = homeViewModel::refresh,
        onBack = onBack
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onMovieClick: (MovieUiModel) -> Unit,
    onRefresh: () -> Unit,
    onBack: () -> Unit,
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state is HomeState.Loading
    )
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh,
    ) {
        when (state) {
            HomeState.Error -> {
                ErrorDialog(
                    message = stringResource(
                        id = R.string.message_loading_error
                    ),
                    positiveAction = DialogAction(text = stringResource(R.string.button_text_positive_error)) {
                        onRefresh()
                    },
                    negativeAction = DialogAction(text = stringResource(R.string.button_text_negative_error)) {
                        onBack()
                    }
                )
            }
            HomeState.Loading -> {
                Center {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            is HomeState.Data -> {
                if (state.values.isEmpty()) {
                    Center {
                        Text(text = stringResource(id = R.string.message_text_empty_result))
                    }
                } else {
                    MovieGridView(
                        movies = state.values,
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

@Composable
fun ErrorDialog(
    message: String,
    positiveAction: DialogAction,
    negativeAction: DialogAction,
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = positiveAction.action) {
                Text(text = positiveAction.text)
            }
        },
        dismissButton = {
            TextButton(onClick = negativeAction.action) {
                Text(text = negativeAction.text)
            }
        },
        text = { Text(text = message) }
    )
}

@Composable
fun Center(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        content()
    }
}