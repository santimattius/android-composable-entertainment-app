package com.santimattius.template.domain.usecases.movies

import com.santimattius.template.domain.repositories.MovieRepository

class FindMovie(private val repository: MovieRepository) {

    suspend operator fun invoke(id: Int) = repository.find(id)
}