package com.santimattius.template.data.datasources.impl

import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.datasources.TvShowRemoteDataSource
import com.santimattius.template.data.entities.TvShowDto

class TMDBTvShowRemoteDataSource(
    private val service: TheMovieDBService,
) : TvShowRemoteDataSource {

    override suspend fun getAll(): Result<List<TvShowDto>> = runCatching {
        val response = service.getTvPopular(page = 1)
        response.results
    }

    override suspend fun find(id: Int): Result<TvShowDto> {
        return Result.failure(NotImplementedError())
    }
}