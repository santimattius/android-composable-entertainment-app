package com.santimattius.template.data

import com.santimattius.template.data.entities.MovieDto
import com.santimattius.template.data.entities.MovieEntity
import com.santimattius.template.domain.entities.Movie

/**
 * map entity to domain model
 *
 * @return movie domain model
 */
internal fun List<MovieEntity>.entityToDomain(): List<Movie> {
    return this.map {
        Movie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            poster = it.poster
        )
    }
}

/**
 * map dto to domain model
 *
 * @return movie domain model
 */
internal fun List<MovieDto>.dtoToDomain(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            overview = it.overview,
            title = it.title,
            poster = it.poster
        )
    }
}

internal fun List<MovieDto>.dtoToEntity(): List<MovieEntity> {
    return map {
        MovieEntity(
            id = it.id,
            title = it.title,
            overview = it.overview,
            poster = it.poster
        )
    }
}