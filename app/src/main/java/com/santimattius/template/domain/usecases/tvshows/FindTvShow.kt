package com.santimattius.template.domain.usecases.tvshows


import com.santimattius.template.domain.repositories.TvShowsRepository

class FindTvShow(private val repository: TvShowsRepository) {

    suspend operator fun invoke(id: Int) = repository.find(id)
}