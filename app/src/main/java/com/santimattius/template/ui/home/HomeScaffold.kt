package com.santimattius.template.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.santimattius.template.R
import com.santimattius.template.domain.entities.Entertainment
import com.santimattius.template.ui.components.*


@Composable
internal fun <T> HomeScaffold(
    state: UiState<List<T>>,
    onMovieClick: (T) -> Unit,
    onRefresh: () -> Unit,
    navigationUp: () -> Unit,
) where T : Entertainment {
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
                    positiveAction = AlertDialog(text = stringResource(R.string.button_text_positive_error)) {
                        onRefresh()
                    },
                    negativeAction = AlertDialog(text = stringResource(R.string.button_text_negative_error)) {
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
                if (state.data.isNullOrEmpty()) {
                    Center {
                        Text(text = stringResource(id = R.string.message_text_empty_result))
                    }
                } else {
                    ContentGridView(
                        entertainmentItems = state.data!!,
                        onItemClick = onMovieClick
                    )
                }

            }
        }
    }
}

@Composable
fun <T : Entertainment> ContentGridView(
    entertainmentItems: List<T>,
    onItemClick: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(R.dimen.item_min_width)),
        contentPadding = PaddingValues(dimensionResource(R.dimen.x_small)),
        modifier = modifier
    ) {
        items(entertainmentItems, key = { it.id }) { item ->
            CardGridItem(item = item, Modifier.clickable { onItemClick(item) })
        }
    }
}