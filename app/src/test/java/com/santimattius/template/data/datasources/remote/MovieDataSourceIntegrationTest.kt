package com.santimattius.template.data.datasources.remote


import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.client.network.service
import com.santimattius.template.data.datasources.implementation.MovieDataSource
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

@ExperimentalCoroutinesApi
class MovieDataSourceIntegrationTest {

    private val jsonLoader = JsonLoader()
    private lateinit var movieDataSource: MovieDataSource
    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val baseUrl = mockWebServer.url("/").toUri().toString()
        val client = service<TheMovieDBService>(baseUrl = baseUrl)
        movieDataSource = MovieDataSource(client)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getMoviePopularSuccess() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonLoader.load("movie_popular_response_success"))

        mockWebServer.enqueue(response)

        runBlocking {
            val result = movieDataSource.getPopularMovies()
            assertThat(result.isSuccess, equalTo(true))
            assertThat(result.getOrNull()?.isNotEmpty(), equalTo(true))
        }
    }

    @Test
    fun getMoviePopularFail() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            .setBody(jsonLoader.load("the_movie_db_response_fail"))

        mockWebServer.enqueue(response)

        runBlocking {
            val result = movieDataSource.getPopularMovies()
            assertThat(result.isFailure, equalTo(true))
        }
    }
}