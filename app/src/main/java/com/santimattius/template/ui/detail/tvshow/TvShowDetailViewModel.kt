package com.santimattius.template.ui.detail.tvshow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.template.domain.usecases.tvshows.FindTvShow
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
class TvShowDetailViewModel @Inject constructor(
    private val findTvShow: FindTvShow,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(TvShowDetailScreenState())
    val state: StateFlow<TvShowDetailScreenState> get() = _state.asStateFlow()

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
            val tvShow = findTvShow(id)
            _state.update {
                if (tvShow == null) {
                    it.copy(
                        isLoading = false,
                        hasError = true
                    )
                } else {
                    it.copy(
                        isLoading = false,
                        hasError = false,
                        data = tvShow
                    )
                }

            }
        }
    }
}