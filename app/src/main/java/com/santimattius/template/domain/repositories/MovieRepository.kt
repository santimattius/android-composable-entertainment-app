package com.santimattius.template.domain.repositories

import com.santimattius.template.domain.entities.Movie

interface MovieRepository {

    suspend fun getPopular(): List<Movie>

    suspend fun fetchPopular(): Result<List<Movie>>
}