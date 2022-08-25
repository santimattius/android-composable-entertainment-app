package com.santimattius.template.data.datasources.impl

import com.santimattius.template.data.client.database.MovieDao
import com.santimattius.template.data.datasources.MovieLocalDataSource
import com.santimattius.template.data.entities.MovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomMovieDataSource(
    private val dao: MovieDao,
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MovieLocalDataSource {

    override suspend fun getAll(): List<MovieEntity> = runWithContext {
        dao.getAll()
    }.getOrDefault(emptyList())

    override suspend fun isEmpty() = runWithContext { count() == 0 }
        .getOrDefault(defaultValue = false)

    override suspend fun save(items: List<MovieEntity>): Result<Boolean> =
        runWithContext {
            deleteAndInsert(*items.toTypedArray()); true
        }

    override suspend fun find(id: Int) =
        runWithContext { findById(id) }
            .fold(
                onSuccess = {
                    if (it == null) {
                        Result.failure(MovieNoExists())
                    } else {
                        Result.success(it)
                    }
                },
                onFailure = { Result.failure(MovieNoExists()) }
            )

    override suspend fun delete(item: MovieEntity) = runWithContext { delete(item); true }

    override suspend fun update(item: MovieEntity) = runWithContext { update(item); true }

    private suspend fun <R> runWithContext(block: suspend MovieDao.() -> R) =
        withContext(dispatcher) {
            dao.runCatching { block() }
        }
}