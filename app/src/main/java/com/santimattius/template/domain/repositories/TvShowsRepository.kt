package com.santimattius.template.domain.repositories

import com.santimattius.template.domain.entities.TvShow

interface TvShowsRepository {

    suspend fun getPopular(): List<TvShow>

    suspend fun fetchPopular(): Result<List<TvShow>>

    suspend fun find(id: Int): TvShow?
}