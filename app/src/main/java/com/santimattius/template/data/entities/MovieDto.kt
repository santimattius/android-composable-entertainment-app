package com.santimattius.template.data.entities

import com.squareup.moshi.Json

data class MovieDto(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "overview")
    val overview: String = "",
    @Json(name = "original_language")
    val originalLanguage: String = "",
    @Json(name = "original_title")
    val originalTitle: String = "",
    @Json(name = "video")
    val video: Boolean = false,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "genre_ids")
    val genreIds: List<Int> = emptyList(),
    @Json(name = "poster_path")
    private val posterPath: String? = null,
    @Json(name = "backdrop_path")
    val backdropPath: String? = null,
    @Json(name = "release_date")
    val releaseDate: String = "",
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "vote_average")
    val voteAverage: Double = 0.0,
    @Json(name = "adult")
    val adult: Boolean = false,
    @Json(name = "vote_count")
    val voteCount: Int = 0,
) {
    val poster: String
        get() = "https://image.tmdb.org/t/p/w500${posterPath}"
}