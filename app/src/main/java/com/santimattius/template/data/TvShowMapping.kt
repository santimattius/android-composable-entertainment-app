package com.santimattius.template.data

import com.santimattius.template.data.entities.TvShowDto
import com.santimattius.template.domain.entities.TvShow

fun List<TvShowDto>.dtoToDomain() = map { it.dtoToDomain() }

fun TvShowDto.dtoToDomain(): TvShow {
    return TvShow(
        id = id,
        title = name,
        overview = overview,
        poster = poster
    )
}