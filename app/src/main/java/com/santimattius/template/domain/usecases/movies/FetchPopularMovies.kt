package com.santimattius.template.domain.usecases.movies

import com.santimattius.template.domain.repositories.MovieRepository

class FetchPopularMovies(private val repository: MovieRepository) {

    suspend operator fun invoke() = repository.fetchPopular()
}