package com.santimattius.template.utils

import com.santimattius.template.data.entities.MovieDto
import com.santimattius.template.data.entities.Response
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object TheMovieDBMother {

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val loader = JsonLoader()
    fun list() = (1..10).map { MovieDto(id = it) }

    fun response(): Response<MovieDto>? {
        val adapter = adapter<MovieDto>()
        return adapter?.fromJson(loader.load("movie_popular_response_success"))
    }

    fun movies() = response()?.results.orEmpty()

    private inline fun <reified T> adapter(): JsonAdapter<Response<T>>? {
        val types = Types.newParameterizedType(Response::class.java, T::class.java)
        return moshi.adapter(types)
    }
}