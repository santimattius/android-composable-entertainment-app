package com.santimattius.template.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.santimattius.template.R
import com.santimattius.template.ui.home.models.MovieUiModel

const val IMAGE_ASPECT_RATIO = 0.67f

@Composable
fun MovieView(movie: MovieUiModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .testTag(movie.title)
            .padding(dimensionResource(R.dimen.item_movie_padding))
    ) {
        SubcomposeAsyncImage(
            model = movie.imageUrl,
            loading = {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .aspectRatio(ratio = IMAGE_ASPECT_RATIO),
        )
    }
}