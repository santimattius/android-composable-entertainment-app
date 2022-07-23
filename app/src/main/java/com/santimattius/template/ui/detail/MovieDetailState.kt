package com.santimattius.template.ui.detail

import com.santimattius.template.domain.entities.Movie

data class MovieDetailState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: Movie? = null,
)