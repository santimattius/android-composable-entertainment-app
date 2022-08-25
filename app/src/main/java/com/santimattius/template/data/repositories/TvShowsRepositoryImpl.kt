package com.santimattius.template.data.repositories

import com.santimattius.template.data.datasources.TvShowLocalDataSource
import com.santimattius.template.data.datasources.TvShowRemoteDataSource
import com.santimattius.template.data.dtoToDomain
import com.santimattius.template.data.dtoToEntity
import com.santimattius.template.data.entityToDomain
import com.santimattius.template.domain.entities.TvShow
import com.santimattius.template.domain.repositories.TvShowsRepository

class TvShowsRepositoryImpl(
    private val localDataSource: TvShowLocalDataSource,
    private val remoteDataSource: TvShowRemoteDataSource,
) : TvShowsRepository {

    override suspend fun getPopular(): List<TvShow> {
        val all = localDataSource.getAll()
        return if (all.isEmpty()) {
            val remotes = remoteDataSource.getAll().getOrDefault(emptyList())
            localDataSource.save(remotes.dtoToEntity())
            remotes.dtoToDomain()
        } else {
            all.entityToDomain()
        }
    }

    override suspend fun fetchPopular(): Result<List<TvShow>> {
        return remoteDataSource.getAll()
            .fold(onSuccess = { remotes ->
                localDataSource.save(remotes.dtoToEntity())
                Result.success(remotes.dtoToDomain())
            }, onFailure = {
                Result.success(localDataSource.getAll().entityToDomain())
            })
    }

    override suspend fun find(id: Int): TvShow? {
        return localDataSource.find(id)
            .fold(onSuccess = {
                it.entityToDomain()
            }, onFailure = {
                val result = remoteDataSource.find(id)
                result.getOrNull()?.dtoToDomain()
            })
    }
}


