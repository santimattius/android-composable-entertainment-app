package com.santimattius.template.ui.home.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.domain.usecases.FetchPopularMovies
import com.santimattius.template.domain.usecases.GetPopularMovies
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
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val fetchPopularMovies: FetchPopularMovies,
) : ViewModel() {

    private val _state = MutableStateFlow(PopularMoviesScreenState.init())
    val state: StateFlow<PopularMoviesScreenState>
        get() = _state.asStateFlow()

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
            val movies = getPopularMovies()
            notify(movies)
        }
    }

    private fun notify(popularMovies: List<Movie>) {
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
            fetchPopularMovies().onSuccess { popularMovies ->
                notify(popularMovies)
            }.onFailure {
                _state.update { it.copy(isLoading = false, isRefreshing = false, hasError = true) }
            }
        }
    }
}