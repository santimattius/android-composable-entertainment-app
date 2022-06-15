package com.santimattius.template.data.repositories

import com.santimattius.template.data.datasources.LocalDataSource
import com.santimattius.template.data.datasources.RemoteDataSource
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

    private val remoteDataSource: RemoteDataSource = mockk()
    private val localDataSource: LocalDataSource = mockk(relaxed = true)
    private val repository = TMDbRepository(remoteDataSource, localDataSource)

    @Test
    fun `get all movies`() {

        coEvery { localDataSource.getAll() } returns emptyList()

        runTest {
            val result = repository.getPopular()
            assertThat(result.isEmpty(), IsEqual(true))
        }

        coVerify { localDataSource.getAll() }
    }

    @Test
    fun `fetch movies and save with response`() {

        coEvery { remoteDataSource.getPopularMovies() } returns Result.success(emptyList())

        runTest {
            val result = repository.fetchPopular()
            assertThat(result.isSuccess, IsEqual(true))
        }

        coVerify { remoteDataSource.getPopularMovies() }
        coVerify { localDataSource.save(any()) }
    }

    @Test
    fun `fetch movies and save without response`() {

        coEvery { remoteDataSource.getPopularMovies() } returns Result.failure(Throwable())

        runTest {
            val result = repository.fetchPopular()
            assertThat(result.isFailure, IsEqual(true))
        }

        coVerify { remoteDataSource.getPopularMovies() }
        coVerify(exactly = 0) { localDataSource.save(any()) }
    }
}