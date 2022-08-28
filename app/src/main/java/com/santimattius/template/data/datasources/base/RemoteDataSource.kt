package com.santimattius.template.data.datasources.base

interface RemoteDataSource<T> {
    suspend fun getAll(): Result<List<T>>
    suspend fun find(id: Int): Result<T>
}