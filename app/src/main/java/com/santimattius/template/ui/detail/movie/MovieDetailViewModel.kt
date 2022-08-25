package com.santimattius.template.ui.detail.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.template.domain.usecases.movies.FindMovie
import com.santimattius.template.ui.navigation.NavArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val findMovie: FindMovie,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> get() = _state.asStateFlow()

    private var job: Job? = null

    init {
        val id = savedStateHandle.get<Int>(NavArg.ItemId.key)
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
                if (movie == null) {
                    it.copy(
                        isLoading = false,
                        hasError = true
                    )
                } else {
                    it.copy(
                        isLoading = false,
                        hasError = false,
                        data = movie
                    )
                }

            }
        }
    }
}