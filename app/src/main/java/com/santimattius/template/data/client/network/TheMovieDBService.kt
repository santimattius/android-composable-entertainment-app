package com.santimattius.template.data.client.network

import com.santimattius.template.data.entities.MovieDto
import com.santimattius.template.data.entities.Response
import com.santimattius.template.data.entities.TvShowDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBService {

    @GET("/{version}/movie/popular")
    suspend fun getMoviePopular(
        @Path("version") version: Int = DEFAULT_VERSION,
        @Query("page") page: Int,
    ): Response<MovieDto>

    @GET("/{version}/movie/{id}")
    suspend fun getMovie(
        @Path("version") version: Int = DEFAULT_VERSION,
        @Path("id") id: Int,
    ): MovieDto


    @GET("/{version}/tv/popular")
    suspend fun getTvPopular(
        @Path("version") version: Int = DEFAULT_VERSION,
        @Query("page") page: Int,
    ): Response<TvShowDto>

    companion object {
        const val DEFAULT_VERSION = 3
    }
}
