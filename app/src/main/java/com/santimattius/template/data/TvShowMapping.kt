package com.santimattius.template.data

import com.santimattius.template.data.entities.TvShowDto
import com.santimattius.template.data.entities.TvShowEntity
import com.santimattius.template.domain.entities.TvShow

fun List<TvShowDto>.dtoToDomain() = map { it.dtoToDomain() }
fun List<TvShowDto>.dtoToEntity() = map { it.dtoToEntity() }
fun List<TvShowEntity>.entityToDomain() = map { it.entityToDomain() }

fun TvShowDto.dtoToDomain(): TvShow {
    return TvShow(
        id = id,
        title = name,
        overview = overview,
        poster = poster
    )
}

fun TvShowDto.dtoToEntity(): TvShowEntity {
    return TvShowEntity(
        id = id,
        title = name,
        overview = overview,
        poster = poster
    )
}

fun TvShowEntity.entityToDomain(): TvShow {
    return TvShow(
        id = id,
        title = title,
        overview = overview,
        poster = poster
    )
}