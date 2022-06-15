package com.santimattius.template.ui.home.viewmodels

import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.domain.repositories.MovieRepository

class FakeMovieRepository(
    private val answers: () -> List<Movie> = { emptyList() },
    private val result: () -> Result<List<Movie>> = { Result.success(answers()) },
) : MovieRepository {

    override suspend fun getPopular() = answers()

    override suspend fun fetchPopular() = result()
}