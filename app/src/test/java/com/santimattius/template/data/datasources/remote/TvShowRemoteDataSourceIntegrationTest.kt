package com.santimattius.template.data.datasources.remote


import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.client.network.service
import com.santimattius.template.data.datasources.impl.TMDBMovieDataSource
import com.santimattius.template.data.datasources.impl.TMDBTvShowRemoteDataSource
import com.santimattius.template.utils.JsonLoader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import kotlin.random.Random

@ExperimentalCoroutinesApi
class TvShowRemoteDataSourceIntegrationTest {

    private val jsonLoader = JsonLoader()
    private lateinit var dataSource: TMDBTvShowRemoteDataSource
    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val baseUrl = mockWebServer.url("/").toUri().toString()
        val client = service<TheMovieDBService>(baseUrl = baseUrl)
        dataSource = TMDBTvShowRemoteDataSource(client)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getTvShowsPopularSuccess() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonLoader.load("tv_popular_response_success"))

        mockWebServer.enqueue(response)

        runBlocking {
            val result = dataSource.getAll()
            assertThat(result.isSuccess, equalTo(true))
            assertThat(result.getOrNull()?.isNotEmpty(), equalTo(true))
        }
    }

    @Test
    fun getTvShowsPopularFail() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            .setBody(jsonLoader.load("the_movie_db_response_fail"))

        mockWebServer.enqueue(response)

        runBlocking {
            val result = dataSource.getAll()
            assertThat(result.isFailure, equalTo(true))
        }
    }

    @Test
    fun findTvShowByIdSuccess() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonLoader.load("tv_show_response"))

        mockWebServer.enqueue(response)

        runBlocking {
            val result = dataSource.find(id = Random.nextInt())
            assertThat(result.isSuccess, equalTo(true))
            assertThat(result.getOrNull()?.id, equalTo(66732))
        }
    }

    @Test
    fun findTvShowByIdFail() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            .setBody(jsonLoader.load("the_movie_db_response_fail"))

        mockWebServer.enqueue(response)

        runBlocking {
            val result = dataSource.find(Random.nextInt())
            assertThat(result.isFailure, equalTo(true))
        }
    }
}