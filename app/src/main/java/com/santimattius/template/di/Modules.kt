package com.santimattius.template.di

import com.santimattius.template.BuildConfig
import com.santimattius.template.data.client.database.AppDataBase
import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.client.network.service
import com.santimattius.template.data.datasources.LocalDataSource
import com.santimattius.template.data.datasources.RemoteDataSource
import com.santimattius.template.data.datasources.implementation.MovieDataSource
import com.santimattius.template.data.datasources.implementation.RoomDataSource
import com.santimattius.template.data.repositories.TMDbRepository
import com.santimattius.template.domain.repositories.MovieRepository
import com.santimattius.template.domain.usecases.FetchPopularMovies
import com.santimattius.template.domain.usecases.GetPopularMovies
import com.santimattius.template.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * ui layer definition module
 */
private val presentationModule = module {
    viewModel {
        HomeViewModel(
            getPopularMovies = get<GetPopularMovies>(),
            fetchPopularMovies = get<FetchPopularMovies>()
        )
    }
}

/**
 * domain layer definition module
 */
private val domainModule = module {
    factory { GetPopularMovies(repository = get<MovieRepository>()) }
    factory { FetchPopularMovies(repository = get<MovieRepository>()) }
}

/**
 * data layer definition module
 */
private val dataModule = module {

    factory<RemoteDataSource> { MovieDataSource(service = get<TheMovieDBService>()) }
    factory<LocalDataSource> { RoomDataSource(dao = AppDataBase.get(androidApplication()).dao()) }

    factory<MovieRepository> {
        TMDbRepository(
            remoteDataSource = get<RemoteDataSource>(),
            localDataSource = get<LocalDataSource>()
        )
    }
}

private const val API_KEY_NAMED = "api_key"

private val theMovieDBModule = module {
    single(named(API_KEY_NAMED)) { BuildConfig.API_KEY }
    single<TheMovieDBService> { service(key = get(named(API_KEY_NAMED))) }
}

internal val moduleDefinitions = listOf(presentationModule, domainModule, dataModule, theMovieDBModule)
