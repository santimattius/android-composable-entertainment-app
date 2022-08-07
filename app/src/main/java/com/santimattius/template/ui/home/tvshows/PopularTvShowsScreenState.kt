package com.santimattius.template.ui.home.tvshows

import com.santimattius.template.domain.entities.TvShow
import com.santimattius.template.ui.components.UiState

data class PopularTvShowsScreenState(
    override val isLoading: Boolean = false,
    override val hasError: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val data: List<TvShow> = emptyList(),
) : UiState<List<TvShow>> {

    companion object {
        fun init() = PopularTvShowsScreenState(isLoading = true)
    }
}
