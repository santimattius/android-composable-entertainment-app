package com.santimattius.template.data.datasources.impl

import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.datasources.MovieRemoteDataSource

internal class TMDBMovieDataSource(
    private val service: TheMovieDBService,
) : MovieRemoteDataSource {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun getAll() = runCatching {
        val response = service.getMoviePopular(page = SINGLE_PAGE)
        response.results
    }

    //TODO:add tests
    override suspend fun find(id: Int) = runCatching {
        service.getMovie(id = id)
    }

    companion object {
        private const val SINGLE_PAGE = 1
    }
}
