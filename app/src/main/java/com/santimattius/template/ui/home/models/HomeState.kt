package com.santimattius.template.ui.home.models

data class HomeState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val isRefreshing: Boolean = false,
    val data: List<MovieUiModel> = emptyList(),
) {
    companion object {
        fun init() = HomeState(isLoading = true)
    }
}
