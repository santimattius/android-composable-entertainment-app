package com.santimattius.template.ui.detail.movie

import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.ui.components.UiState

data class MovieDetailState(
    override val isLoading: Boolean = false,
    override val hasError: Boolean = false,
    override val data: Movie = Movie.empty(),
    override val isRefreshing: Boolean = false,
) : UiState<Movie>