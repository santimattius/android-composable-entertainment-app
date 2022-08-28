package com.santimattius.template.data.datasources.impl

import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.datasources.TvShowRemoteDataSource

class TMDBTvShowRemoteDataSource(
    private val service: TheMovieDBService,
) : TvShowRemoteDataSource {

    override suspend fun getAll() = runCatching {
        val response = service.getTvPopular(page = 1)
        response.results
    }

    override suspend fun find(id: Int) = runCatching {
        service.getTvShow(id = id)
    }
}