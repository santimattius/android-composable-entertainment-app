package com.santimattius.template.data.datasources

import com.santimattius.template.data.entities.MovieDto

interface RemoteDataSource {
    suspend fun getPopularMovies(): Result<List<MovieDto>>
}