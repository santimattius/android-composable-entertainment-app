package com.santimattius.template.ui.home.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.template.domain.entities.TvShow
import com.santimattius.template.domain.usecases.tvshows.FetchPopularTvShows
import com.santimattius.template.domain.usecases.tvshows.GetPopularTvShows
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularTvShowsViewModel @Inject constructor(
    private val getPopularTvShows: GetPopularTvShows,
    private val fetchPopularTvShows: FetchPopularTvShows,
) : ViewModel() {

    private val _state = MutableStateFlow(PopularTvShowsScreenState.init())
    val state: StateFlow<PopularTvShowsScreenState> get() = _state.asStateFlow()

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.update { it.copy(isLoading = false, hasError = true) }
    }

    init {
        popularMovies()
    }

    private fun popularMovies() {
        _state.update {
            it.copy(
                isLoading = true,
                isRefreshing = false
            )
        }
        viewModelScope.launch(exceptionHandler) {
            val popularsTvShows = getPopularTvShows()
            notify(popularsTvShows)
        }
    }

    private fun notify(popularMovies: List<TvShow>) {
        _state.update {
            it.copy(
                isRefreshing = false,
                isLoading = false,
                data = popularMovies
            )
        }
    }

    fun refresh() {
        _state.update { it.copy(isRefreshing = true) }
        fetch()
    }

    private fun fetch() {
        job?.cancel()
        job = viewModelScope.launch(exceptionHandler) {
            fetchPopularTvShows().onSuccess { popularTvShows ->
                notify(popularTvShows)
            }.onFailure {
                _state.update { it.copy(isLoading = false, isRefreshing = false, hasError = true) }
            }
        }
    }
}