package com.santimattius.template.data.repositories

import com.santimattius.template.data.datasources.MovieLocalDataSource
import com.santimattius.template.data.datasources.MovieRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test

@ExperimentalCoroutinesApi
class TMDbRepositoryTest {

    private val movieRemoteDataSource: MovieRemoteDataSource = mockk()
    private val movieLocalDataSource: MovieLocalDataSource = mockk(relaxed = true)
    private val repository = MoviesRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)

    @Test
    fun `get all movies`() {

        coEvery { movieLocalDataSource.getAll() } returns emptyList()

        runTest {
            val result = repository.getPopular()
            assertThat(result.isEmpty(), IsEqual(true))
        }

        coVerify { movieLocalDataSource.getAll() }
    }

    @Test
    fun `fetch movies and save with response`() {

        coEvery { movieRemoteDataSource.getAll() } returns Result.success(emptyList())

        runTest {
            val result = repository.fetchPopular()
            assertThat(result.isSuccess, IsEqual(true))
        }

        coVerify { movieRemoteDataSource.getAll() }
        coVerify { movieLocalDataSource.save(any()) }
    }

    @Test
    fun `fetch movies and save without response`() {

        coEvery { movieRemoteDataSource.getAll() } returns Result.failure(Throwable())

        runTest {
            val result = repository.fetchPopular()
            assertThat(result.isFailure, IsEqual(true))
        }

        coVerify { movieRemoteDataSource.getAll() }
        coVerify(exactly = 0) { movieLocalDataSource.save(any()) }
    }
}