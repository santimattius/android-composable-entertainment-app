package com.santimattius.template.data.datasources.local

import com.santimattius.template.data.entities.TvShowEntity

object TvShowEntityMother {
    private const val MAX = 10
    private const val MIN = 1

    fun tvShows() = (MIN..MAX).map {
        TvShowEntity(
            id = it,
            title = "Title $it",
            overview = "Overview $it",
            poster = "Poster $it"
        )
    }

    fun tvShow() = tvShows().first()
}
