package com.santimattius.template.data.datasources.remote

import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.datasources.implementation.MovieDataSource
import com.santimattius.template.data.entities.Response
import com.santimattius.template.utils.TheMovieDBMother
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDataSourceTest {

    private val client: TheMovieDBService = mockk()
    private val movieDataSource = MovieDataSource(client)

    @Test
    fun `get populars movie on client result is success`() {
        val pictures = TheMovieDBMother.list()

        coEvery {
            client.getMoviePopular(any(), any())
        } returns Response(results = pictures)

        runTest {
            val result = movieDataSource.getPopularMovies()
            assertThat(result.isSuccess, IsEqual(true))
        }

        coVerify { client.getMoviePopular(any(), any()) }
    }

    @Test
    fun `get popular movie on client result is fail`() {
        coEvery { client.getMoviePopular(any(), any()) } throws Throwable()
        runTest {
            val result = movieDataSource.getPopularMovies()
            assertThat(result.isFailure, IsEqual(true))
        }
        coVerify { client.getMoviePopular(any(), any()) }
    }
}