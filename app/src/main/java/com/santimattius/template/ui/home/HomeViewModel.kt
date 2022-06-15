package com.santimattius.template.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.template.domain.usecases.FetchPopularMovies
import com.santimattius.template.domain.usecases.GetPopularMovies
import com.santimattius.template.ui.home.models.HomeState
import com.santimattius.template.ui.home.models.mapping.asUiModels
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPopularMovies: GetPopularMovies,
    private val fetchPopularMovies: FetchPopularMovies,
) : ViewModel() {

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState>
        get() = _state

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.postValue(HomeState.Error)
    }

    init {
        popularMovies()
    }

    private fun popularMovies() {
        _state.postValue(HomeState.Loading)
        viewModelScope.launch(exceptionHandler) {
            val popularMovies = getPopularMovies()
            _state.postValue(HomeState.Data(values = popularMovies.asUiModels()))
        }
    }

    fun refresh() {
        _state.postValue(HomeState.Refreshing)
        fetch()
    }

    private fun fetch() {
        job?.cancel()
        job = viewModelScope.launch(exceptionHandler) {
            fetchPopularMovies().onSuccess { popularMovies ->
                _state.postValue(HomeState.Data(values = popularMovies.asUiModels()))
            }.onFailure {
                _state.postValue(HomeState.Error)
            }
        }
    }
}