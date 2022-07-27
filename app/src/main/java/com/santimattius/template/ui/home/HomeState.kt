package com.santimattius.template.ui.home

import com.santimattius.template.domain.entities.Movie

data class HomeState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val isRefreshing: Boolean = false,
    val data: List<Movie> = emptyList(),
) {
    companion object {
        fun init() = HomeState(isLoading = true)
    }
}
