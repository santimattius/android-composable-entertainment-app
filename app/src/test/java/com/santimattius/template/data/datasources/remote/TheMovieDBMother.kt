package com.santimattius.template.data.datasources.remote

import com.santimattius.template.data.entities.MovieDto
import com.santimattius.template.data.entities.Response
import com.santimattius.template.data.entities.TvShowDto
import com.santimattius.template.utils.JsonLoader
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object TheMovieDBMother {

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val loader = JsonLoader()

    fun fakeMovies() = (1..10).map { MovieDto(id = it) }

    fun fakeTvShows() = (1..10).map { TvShowDto(id = it) }

    private fun moviesResponse(): Response<MovieDto>? {
        val adapter = adapter<MovieDto>()
        return adapter?.fromJson(loader.load("movie_popular_response_success"))
    }

    private fun tvShowsResponse(): Response<TvShowDto>? {
        val adapter = adapter<TvShowDto>()
        return adapter?.fromJson(loader.load("tv_popular_response_success"))
    }

    fun movies() = moviesResponse()?.results.orEmpty()

    fun tvShows() = tvShowsResponse()?.results.orEmpty()

    private inline fun <reified T> adapter(): JsonAdapter<Response<T>>? {
        val types = Types.newParameterizedType(Response::class.java, T::class.java)
        return moshi.adapter(types)
    }
}