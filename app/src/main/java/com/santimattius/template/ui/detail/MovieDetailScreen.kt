package com.santimattius.template.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.santimattius.template.R
import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.ui.home.Center

@ExperimentalLifecycleComposeApi
@Composable
fun MovieDetailRoute(
    viewModel: MovieDetailViewModel,
    navigateUp: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar {
                ArrowBackIcon {
                    navigateUp()
                }
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                val state by viewModel.state.collectAsStateWithLifecycle()
                MovieDetailStates(state) { movie ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium)),
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .fillMaxSize()
                            .background(MaterialTheme.colors.surface)
                    ) {
                        SubcomposeAsyncImage(
                            model = movie.poster,
                            loading = {
                                Box(contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colors.secondary,
                                        modifier = Modifier.size(size = 32.dp)
                                    )
                                }
                            },
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth(fraction = 0.6f)
                                .padding(all = 8.dp)
                                .aspectRatio(ratio = 0.67f)

                        )
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(
                                horizontal = dimensionResource(R.dimen.medium),
                                vertical = dimensionResource(R.dimen.none)
                            )
                        )
                        Text(
                            text = movie.overview,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = dimensionResource(R.dimen.medium),
                                    vertical = dimensionResource(R.dimen.none)
                                )
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun MovieDetailStates(
    state: MovieDetailState,
    content: @Composable (Movie) -> Unit,
) {
    when {
        state.isLoading -> {
            Center {
                CircularProgressIndicator()
            }
        }
        state.hasError -> {
            Center {
                Text(stringResource(id = R.string.message_text_empty_result))
            }
        }
        else -> {
            content(state.data)
        }
    }
}