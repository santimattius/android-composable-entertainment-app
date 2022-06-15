package com.santimattius.template.data.datasources.implementation

import com.santimattius.template.data.entities.MovieEntity

object MovieEntityMother {
    private const val MAX = 10
    private const val MIN = 1

    fun movies() = (MIN..MAX).map {
        MovieEntity(
            id = it,
            title = "Title $it",
            overview = "Overview $it",
            poster = "Poster $it"
        )
    }

    fun movie() = movies().first()
}
