package com.santimattius.template.data.datasources

import com.santimattius.template.data.entities.MovieEntity

interface LocalDataSource {

    suspend fun getAll(): List<MovieEntity>

    suspend fun isEmpty(): Boolean

    suspend fun save(movies: List<MovieEntity>): Result<Boolean>

    suspend fun find(id: Int): Result<MovieEntity>

    suspend fun delete(movie: MovieEntity): Result<Boolean>

    suspend fun update(movie: MovieEntity): Result<Boolean>
}