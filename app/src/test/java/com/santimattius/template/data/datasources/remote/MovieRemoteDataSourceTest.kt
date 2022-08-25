package com.santimattius.template.data.datasources.remote

import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.datasources.impl.TMDBMovieDataSource
import com.santimattius.template.data.entities.Response
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRemoteDataSourceTest {

    private val client: TheMovieDBService = mockk()
    private val movieDataSource = TMDBMovieDataSource(client)

    @Test
    fun `get populars movie on client result is success`() {
        val movies = TheMovieDBMother.fakeMovies()

        coEvery {
            client.getMoviePopular(any(), any())
        } returns Response(results = movies)

        runTest {
            val result = movieDataSource.getAll()
            assertThat(result.isSuccess, IsEqual(true))
        }

        coVerify { client.getMoviePopular(any(), any()) }
    }

    @Test
    fun `get popular movie on client result is fail`() {
        coEvery { client.getMoviePopular(any(), any()) } throws Throwable()
        runTest {
            val result = movieDataSource.getAll()
            assertThat(result.isFailure, IsEqual(true))
        }
        coVerify { client.getMoviePopular(any(), any()) }
    }

    @Test
    fun `find movie by id on client result is success`() {

        val movie = TheMovieDBMother.fakeMovies().random()

        coEvery {
            client.getMovie(id = movie.id)
        } returns movie

        runTest {
            val result = movieDataSource.find(movie.id)
            assertThat(result.isSuccess, IsEqual(true))
            assertThat(result.getOrNull(), IsEqual(movie))
        }

        coVerify { client.getMovie(id = movie.id) }
    }

    @Test
    fun `find movie by id on client result is fail`() {
        val movie = TheMovieDBMother.fakeMovies().random()

        coEvery {
            client.getMovie(id = movie.id)
        } throws Throwable()

        runTest {
            val result = movieDataSource.find(movie.id)
            assertThat(result.isSuccess, IsEqual(false))
        }

        coVerify { client.getMovie(id = movie.id) }
    }
}