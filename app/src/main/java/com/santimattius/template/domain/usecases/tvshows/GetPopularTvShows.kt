package com.santimattius.template.domain.usecases.tvshows

import com.santimattius.template.domain.repositories.TvShowsRepository

class GetPopularTvShows(private val repository: TvShowsRepository) {

    suspend operator fun invoke() = repository.getPopular()
}