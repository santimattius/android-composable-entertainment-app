package com.santimattius.template.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.santimattius.template.R
import com.santimattius.template.domain.entities.Entertainment
import com.santimattius.template.ui.components.Center
import com.santimattius.template.ui.components.UiState


@Composable
fun <T : Entertainment> DetailScaffold(
    state: UiState<T>,
) {
    val scrollState = rememberScrollState()

    DetailStates(state) { movie ->
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

@Composable
private fun <T : Entertainment> DetailStates(
    state: UiState<T>,
    content: @Composable (T) -> Unit,
) {
    when {
        state.isLoading -> {
            Center {
                CircularProgressIndicator()
            }
        }
        state.hasError || state.data == null -> {
            Center {
                Text(stringResource(id = R.string.message_text_empty_result))
            }
        }
        else -> {
            content(state.data!!)
        }
    }
}