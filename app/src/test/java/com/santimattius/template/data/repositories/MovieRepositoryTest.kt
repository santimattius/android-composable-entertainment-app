package com.santimattius.template.data.repositories

import com.santimattius.template.data.datasources.MovieLocalDataSource
import com.santimattius.template.data.datasources.MovieRemoteDataSource
import com.santimattius.template.data.datasources.impl.MovieNoExists
import com.santimattius.template.data.datasources.local.MovieEntityMother
import com.santimattius.template.data.datasources.remote.TheMovieDBMother
import com.santimattius.template.data.dtoToDomain
import com.santimattius.template.data.entityToDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNull.nullValue
import org.junit.Test
import kotlin.random.Random

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private val movieRemoteDataSource: MovieRemoteDataSource = mockk()
    private val movieLocalDataSource: MovieLocalDataSource = mockk(relaxed = true)
    private val repository = MoviesRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)

    private val movies = TheMovieDBMother.movies()
    private val moviesEntities = MovieEntityMother.movies()

    @Test
    fun `get all movies`() {

        coEvery {
            movieLocalDataSource.isEmpty()
        } returns moviesEntities.isEmpty()

        coEvery {
            movieLocalDataSource.getAll()
        } returns moviesEntities

        runTest {
            val result = repository.getPopular()
            assertThat(result.isNotEmpty(), IsEqual(true))
        }

        coVerify { movieLocalDataSource.getAll() }
    }

    @Test
    fun `get all tv show when empty cache`() {

        coEvery {
            movieLocalDataSource.isEmpty()
        } returns true

        coEvery {
            movieLocalDataSource.getAll()
        } returns moviesEntities

        coEvery {
            movieRemoteDataSource.getAll()
        } returns Result.success(movies)

        runTest {
            val result = repository.getPopular()
            assertThat(result.isNotEmpty(), CoreMatchers.equalTo(true))
        }

        coVerify { movieLocalDataSource.isEmpty() }
        coVerify { movieRemoteDataSource.getAll() }
        coVerify { movieLocalDataSource.save(any()) }
        coVerify { movieLocalDataSource.getAll() }
    }

    @Test
    fun `fetch movies and save with response`() {

        coEvery {
            movieRemoteDataSource.getAll()
        } returns Result.success(movies)

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

    @Test
    fun `find movie from local`() {
        val movie = MovieEntityMother.movie()
        coEvery { movieLocalDataSource.find(movie.id) } returns Result.success(movie)

        runTest {
            val movieSearched = repository.find(movie.id)
            assertThat(movieSearched, IsEqual(movie.entityToDomain()))
        }

        coVerify { movieLocalDataSource.find(movie.id) }
    }

    @Test
    fun `find movie when not local exists and call service`() {
        val movie = movies.first()
        coEvery { movieLocalDataSource.find(movie.id) } returns Result.failure(MovieNoExists())
        coEvery { movieRemoteDataSource.find(movie.id) } returns Result.success(movie)

        runTest {
            val movieSearched = repository.find(movie.id)
            assertThat(movieSearched, IsEqual(movie.dtoToDomain()))
        }

        coVerify { movieLocalDataSource.find(movie.id) }
        coVerify { movieRemoteDataSource.find(movie.id) }
    }

    @Test
    fun `find movie when not local exists and call service return error`() {
        val movieId = Random.nextInt()

        coEvery { movieLocalDataSource.find(movieId) } returns Result.failure(MovieNoExists())
        coEvery { movieRemoteDataSource.find(movieId) } returns Result.failure(Throwable())

        runTest {
            val movieSearched = repository.find(movieId)
            assertThat(movieSearched, nullValue())
        }

        coVerify { movieLocalDataSource.find(movieId) }
        coVerify { movieRemoteDataSource.find(movieId) }
    }
}