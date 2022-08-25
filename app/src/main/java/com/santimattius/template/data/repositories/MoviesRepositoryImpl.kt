package com.santimattius.template.data.repositories

import com.santimattius.template.data.datasources.MovieLocalDataSource
import com.santimattius.template.data.datasources.MovieRemoteDataSource
import com.santimattius.template.data.dtoToDomain
import com.santimattius.template.data.dtoToEntity
import com.santimattius.template.data.entityToDomain
import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.domain.repositories.MovieRepository

internal class MoviesRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
) : MovieRepository {

    override suspend fun getPopular(): List<Movie> {
        if (movieLocalDataSource.isEmpty()) {
            val movies = movieRemoteDataSource.getAll().getOrDefault(emptyList())
            movieLocalDataSource.save(movies.dtoToEntity())
        }
        return movieLocalDataSource.getAll().entityToDomain()
    }

    override suspend fun fetchPopular() = movieRemoteDataSource.getAll().fold(onSuccess = {
        movieLocalDataSource.save(it.dtoToEntity())
        Result.success(movieLocalDataSource.getAll().entityToDomain())
    }, onFailure = {
        Result.failure(RefreshMovieFailed())
    })

    override suspend fun find(id: Int): Movie? {
        return movieLocalDataSource.find(id).fold(
            onSuccess = {
                it.entityToDomain()
            },
            onFailure = {
                val result = movieRemoteDataSource.find(id)
                result.getOrNull()?.dtoToDomain()
            }
        )
    }

}
