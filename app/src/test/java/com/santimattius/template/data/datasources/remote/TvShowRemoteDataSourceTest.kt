package com.santimattius.template.data.datasources.remote

import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.datasources.impl.TMDBTvShowRemoteDataSource
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
class TvShowRemoteDataSourceTest {

    private val client: TheMovieDBService = mockk()
    private val dataSource = TMDBTvShowRemoteDataSource(client)

    @Test
    fun `get populars movie on client result is success`() {
        val tvShows = TheMovieDBMother.fakeTvShows()

        coEvery {
            client.getTvPopular(any(), any())
        } returns Response(results = tvShows)

        runTest {
            val result = dataSource.getAll()
            assertThat(result.isSuccess, IsEqual(true))
        }

        coVerify { client.getTvPopular(any(), any()) }
    }

    @Test
    fun `get popular movie on client result is fail`() {
        coEvery { client.getTvPopular(any(), any()) } throws Throwable()

        runTest {
            val result = dataSource.getAll()
            assertThat(result.isFailure, IsEqual(true))
        }
        coVerify { client.getTvPopular(any(), any()) }
    }

    @Test
    fun `find movie by id on client result is success`() {

        val tvShow = TheMovieDBMother.fakeTvShows().random()

        coEvery {
            client.getTvShow(id = tvShow.id)
        } returns tvShow

        runTest {
            val result = dataSource.find(tvShow.id)
            assertThat(result.isSuccess, IsEqual(true))
            assertThat(result.getOrNull(), IsEqual(tvShow))
        }

        coVerify { client.getTvShow(id = tvShow.id) }
    }

    @Test
    fun `find movie by id on client result is fail`() {
        val tvShow = TheMovieDBMother.fakeTvShows().random()

        coEvery {
            client.getTvShow(id = tvShow.id)
        } throws Throwable()

        runTest {
            val result = dataSource.find(tvShow.id)
            assertThat(result.isSuccess, IsEqual(false))
        }

        coVerify { client.getTvShow(id = tvShow.id) }
    }
}