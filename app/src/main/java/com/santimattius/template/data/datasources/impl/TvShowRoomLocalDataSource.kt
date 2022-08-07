package com.santimattius.template.data.datasources.impl

import com.santimattius.template.data.client.database.TvShowDao
import com.santimattius.template.data.datasources.TvShowLocalDataSource
import com.santimattius.template.data.entities.TvShowEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TvShowRoomLocalDataSource(
    private val dao: TvShowDao,
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TvShowLocalDataSource {

    override suspend fun getAll() = runWithContext {
        dao.getAll()
    }.getOrDefault(emptyList())

    override suspend fun isEmpty() = runWithContext { count() == 0 }
        .getOrDefault(defaultValue = false)

    override suspend fun save(items: List<TvShowEntity>) = runWithContext {
        deleteAndInsert(*items.toTypedArray()); true
    }

    override suspend fun find(id: Int) = runWithContext { findById(id) }
        .fold(
            onSuccess = {
                if (it == null) {
                    Result.failure(TvShowNoExists())
                } else {
                    Result.success(it)
                }
            },
            onFailure = { Result.failure(TvShowNoExists()) }
        )

    override suspend fun delete(item: TvShowEntity) = runWithContext { delete(item);true }

    override suspend fun update(item: TvShowEntity) = runWithContext { update(item);true }

    private suspend fun <R> runWithContext(block: suspend TvShowDao.() -> R) =
        withContext(dispatcher) {
            dao.runCatching { block() }
        }
}