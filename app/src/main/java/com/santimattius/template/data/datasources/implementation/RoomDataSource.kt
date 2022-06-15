package com.santimattius.template.data.datasources.implementation

import com.santimattius.template.data.client.database.MovieDao
import com.santimattius.template.data.datasources.LocalDataSource
import com.santimattius.template.data.entities.MovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(
    private val dao: MovieDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : LocalDataSource {

    override suspend fun getAll(): List<MovieEntity> = withContext(dispatcher) {
        dao.getAll()
    }

    override suspend fun isEmpty() = runWithContext { count() == 0 }
        .getOrDefault(defaultValue = false)

    override suspend fun save(movies: List<MovieEntity>): Result<Boolean> =
        runWithContext {
            deleteAndInsert(*movies.toTypedArray()); true
        }

    override suspend fun find(id: Int) =
        runWithContext { findById(id) }.fold(onSuccess = {
            if (it == null) Result.failure(MovieNoExists())
            else Result.success(it)
        }, onFailure = { Result.failure(MovieNoExists()) })

    override suspend fun delete(movie: MovieEntity) = runWithContext { delete(movie); true }

    override suspend fun update(movie: MovieEntity) = runWithContext { update(movie); true }

    private suspend fun <R> runWithContext(block: suspend MovieDao.() -> R) =
        withContext(dispatcher) {
            dao.runCatching { block() }
        }
}