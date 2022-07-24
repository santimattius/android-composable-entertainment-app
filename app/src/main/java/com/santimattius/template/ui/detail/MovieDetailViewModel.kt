package com.santimattius.template.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.template.domain.usecases.FindMovie
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    handle: SavedStateHandle,
    private val findMovie: FindMovie,
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> get() = _state.asStateFlow()

    private var job: Job? = null

    init {
        val id = handle.get<Int>("id")
        if (id == null) {
            _state.update { it.copy(hasError = true) }
        } else {
            load(id)
        }
    }

    private fun load(id: Int) {
        _state.update { it.copy(isLoading = true) }
        job?.cancel()
        job = viewModelScope.launch {
            val movie = findMovie(id)
            _state.update {
                it.copy(
                    isLoading = false,
                    hasError = movie == null,
                    data = movie
                )
            }
        }
    }
}