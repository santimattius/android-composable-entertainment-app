package com.santimattius.template.ui.home.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.santimattius.template.domain.usecases.FetchPopularMovies
import com.santimattius.template.domain.usecases.GetPopularMovies
import com.santimattius.template.ui.home.HomeViewModel
import com.santimattius.template.ui.home.HomeState
import com.santimattius.template.utils.MainCoroutinesTestRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = MainCoroutinesTestRule()

    @Test
    fun `check case when init view model`() {

        val userCase = GetPopularMovies(repository = FakeMovieRepository(answers = { emptyList() }))

        val viewModel = HomeViewModel(userCase, mockk())

        runTest {
            val state = viewModel.state.value
            assertThat(state, IsEqual(HomeState(data = emptyList())))
        }

    }

    @Test
    fun `check when init fail with exception`() {

        val userCase =
            GetPopularMovies(repository = FakeMovieRepository(answers = { throw TestException() }))

        val viewModel = HomeViewModel(userCase, mockk())
        runTest {
            val state = viewModel.state.value
            assertThat(state, IsEqual(HomeState(hasError = true)))
        }
    }

    @Test
    fun `check case with retry`() {

        val userCase = FetchPopularMovies(repository = FakeMovieRepository(result = {
            Result.failure(TestException())
        }))

        val viewModel = HomeViewModel(mockk(), userCase)

        viewModel.refresh()

        runTest {
            val state = viewModel.state.value
            assertThat(state, IsEqual(HomeState(hasError = true)))
        }
    }

    class TestException : Throwable()
}