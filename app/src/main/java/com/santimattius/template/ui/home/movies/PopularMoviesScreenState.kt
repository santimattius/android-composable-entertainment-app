package com.santimattius.template.ui.home.movies

import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.ui.components.UiState

data class PopularMoviesScreenState(
    override val isLoading: Boolean = false,
    override val hasError: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val data: List<Movie> = emptyList(),
) : UiState<List<Movie>> {

    companion object {
        fun init() = PopularMoviesScreenState(isLoading = true)
    }
}
