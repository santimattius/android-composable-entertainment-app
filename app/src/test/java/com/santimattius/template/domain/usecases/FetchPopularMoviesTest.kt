package com.santimattius.template.domain.usecases

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

    @Test
    fun `verify call repository`() {
        val repository = mockk<MovieRepository>(relaxed = true)
        coEvery { repository.fetchPopular() } returns Result.success(emptyList())
        val fetchPopularMovies = FetchPopularMovies(repository)
        runTest {
            assertThat(fetchPopularMovies(), IsEqual(Result.success(emptyList())))
        }
        coVerify { repository.fetchPopular() }
    }
}