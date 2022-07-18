package com.santimattius.template.ui.home.models

sealed class HomeState {
    object Loading : HomeState()
    object Error : HomeState()
    data class Data(val values: List<MovieUiModel>) : HomeState()
}
