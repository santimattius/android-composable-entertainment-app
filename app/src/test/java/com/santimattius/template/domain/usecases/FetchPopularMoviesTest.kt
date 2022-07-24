package com.santimattius.template.domain.usecases

import com.santimattius.template.data.repositories.RefreshMovieFailed
import com.santimattius.template.domain.repositories.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchPopularMoviesTest {
    private val repository = mockk<MovieRepository>(relaxed = true)
    private val fetchPopularMovies = FetchPopularMovies(repository)

    @Test
    fun `verify call repository when result success`() {

        coEvery {
            repository.fetchPopular()
        } returns Result.success(emptyList())

        runTest {
            assertThat(fetchPopularMovies(), IsEqual(Result.success(emptyList())))
        }
        coVerify { repository.fetchPopular() }
    }

    @Test
    fun `verify call repository when result fail`() {

        coEvery {
            repository.fetchPopular()
        } returns Result.failure(RefreshMovieFailed())

        runTest {
            assertThat(fetchPopularMovies().isFailure, IsEqual(true))
        }
        coVerify { repository.fetchPopular() }
    }
}