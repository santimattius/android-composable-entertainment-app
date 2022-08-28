package com.santimattius.template.ui.detail.tvshow

import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.domain.entities.TvShow
import com.santimattius.template.ui.components.UiState

data class TvShowDetailScreenState(
    override val isLoading: Boolean = false,
    override val hasError: Boolean = false,
    override val data: TvShow = TvShow.empty(),
    override val isRefreshing: Boolean = false,
) : UiState<TvShow>