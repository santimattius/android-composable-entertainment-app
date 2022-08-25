package com.santimattius.template.data.datasources.base

import kotlinx.coroutines.CoroutineDispatcher

interface LocalDataSource<T> {

    val dispatcher: CoroutineDispatcher

    suspend fun getAll(): List<T>

    suspend fun isEmpty(): Boolean

    suspend fun save(items: List<T>): Result<Boolean>

    suspend fun find(id: Int): Result<T>

    suspend fun delete(item: T): Result<Boolean>

    suspend fun update(item: T): Result<Boolean>
}