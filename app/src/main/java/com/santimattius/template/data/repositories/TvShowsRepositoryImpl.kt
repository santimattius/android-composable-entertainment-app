package com.santimattius.template.data.repositories

import com.santimattius.template.data.datasources.TvShowLocalDataSource
import com.santimattius.template.data.datasources.TvShowRemoteDataSource
import com.santimattius.template.data.dtoToDomain
import com.santimattius.template.domain.entities.TvShow
import com.santimattius.template.domain.repositories.TvShowsRepository

class TvShowsRepositoryImpl(
    private val localDataSource: TvShowLocalDataSource,
    private val remoteDataSource: TvShowRemoteDataSource,
) : TvShowsRepository {

    override suspend fun getPopular(): List<TvShow> {
        return remoteDataSource.getAll()
            .fold(onSuccess = {
                it.dtoToDomain()
            }, onFailure = {
                emptyList()
            })
    }

    override suspend fun fetchPopular(): Result<List<TvShow>> {
        return remoteDataSource.getAll()
            .fold(onSuccess = {
                Result.success(it.dtoToDomain())
            }, onFailure = {
                Result.failure(it)
            })
    }

    override suspend fun find(id: Int): TvShow? {
        return remoteDataSource.find(id)
            .fold(onSuccess = {
                it.dtoToDomain()
            }, onFailure = {
                null
            })
    }
}


