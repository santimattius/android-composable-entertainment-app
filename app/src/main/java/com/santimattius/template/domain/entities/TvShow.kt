package com.santimattius.template.domain.entities

data class TvShow(
    override val id: Int,
    override val overview: String,
    override val title: String,
    override val poster: String,
) : Entertainment {
    companion object {
        fun empty() = TvShow(
            id = -1,
            overview = "",
            title = "",
            poster = ""
        )
    }
}