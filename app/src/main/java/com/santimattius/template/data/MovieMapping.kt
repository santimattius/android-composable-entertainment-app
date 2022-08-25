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
    return this.map { it.entityToDomain() }
}

internal fun MovieEntity.entityToDomain() = Movie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    poster = this.poster
)

/**
 * map dto to domain model
 *
 * @return movie domain model
 */
internal fun List<MovieDto>.dtoToDomain(): List<Movie> {
    return map { it.dtoToDomain() }
}

internal fun MovieDto.dtoToDomain(): Movie {
    return Movie(
        id = this.id,
        overview = this.overview,
        title = this.title,
        poster = this.poster
    )
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