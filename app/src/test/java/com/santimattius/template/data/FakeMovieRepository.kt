package com.santimattius.template.data

import com.santimattius.template.data.datasources.remote.TheMovieDBMother
import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.domain.repositories.MovieRepository

class FakeMovieRepository @JvmOverloads constructor(
    private val answers: () -> List<Movie> = { TheMovieDBMother.movies().dtoToDomain() },
    private val result: () -> Result<List<Movie>> = { Result.success(answers()) },
) : MovieRepository {

    override suspend fun getPopular() = answers()

    override suspend fun fetchPopular() = result()

    override suspend fun find(id: Int): Movie? {
        return getPopular().firstOrNull { it.id == id }
    }
}