package com.santimattius.template.data.datasources.implementation

import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.datasources.RemoteDataSource

internal class MovieDataSource(
    private val service: TheMovieDBService,
) : RemoteDataSource {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun getPopularMovies() = runCatching {
        val response = service.getMoviePopular(version = DEFAULT_VERSION, page = SINGLE_PAGE)
        response.results
    }

    override suspend fun findMovie(id: Int) = runCatching {
        val response = service.getMovie(version = DEFAULT_VERSION, id = id)
        response
    }


    companion object {
        private const val SINGLE_PAGE = 1
        const val DEFAULT_VERSION = 3
    }
}
