package com.santimattius.template.domain.entities

data class Movie(
    override val id: Int,
    override val overview: String,
    override val title: String,
    override val poster: String,
) : Entertainment {
    companion object {
        fun empty() = Movie(
            id = -1,
            overview = "",
            title = "",
            poster = ""
        )
    }
}