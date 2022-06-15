package com.santimattius.template.domain.usecases

import com.santimattius.template.domain.repositories.MovieRepository

class GetPopularMovies(private val repository: MovieRepository) {

    suspend operator fun invoke() = repository.getPopular()
}