package com.santimattius.template.ui.components

interface UiState<T> {
    val isLoading: Boolean
    val hasError: Boolean
    val isRefreshing: Boolean
    val data: T?
}
