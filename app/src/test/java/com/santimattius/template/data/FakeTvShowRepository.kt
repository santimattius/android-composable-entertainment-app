package com.santimattius.template.data

import com.santimattius.template.data.datasources.remote.TheMovieDBMother
import com.santimattius.template.domain.entities.TvShow
import com.santimattius.template.domain.repositories.TvShowsRepository

class FakeTvShowRepository @JvmOverloads constructor(
    private val answers: () -> List<TvShow> = { TheMovieDBMother.tvShows().dtoToDomain() },
    private val result: () -> Result<List<TvShow>> = { Result.success(answers()) },
) : TvShowsRepository {

    override suspend fun getPopular() = answers()

    override suspend fun fetchPopular() = result()

    override suspend fun find(id: Int): TvShow? {
        return getPopular().firstOrNull { it.id == id }
    }
}