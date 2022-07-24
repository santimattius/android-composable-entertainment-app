package com.santimattius.template.domain.entities

data class Movie(
    val id: Int,
    val overview: String,
    val title: String,
    val poster: String,
) {
    companion object {
        fun empty() = Movie(
            id = -1,
            overview = "",
            title = "",
            poster = ""
        )
    }
}