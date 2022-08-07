package com.santimattius.template.di

import com.santimattius.template.data.datasources.MovieLocalDataSource
import com.santimattius.template.data.datasources.MovieRemoteDataSource
import com.santimattius.template.data.datasources.TvShowLocalDataSource
import com.santimattius.template.data.datasources.TvShowRemoteDataSource
import com.santimattius.template.data.repositories.MoviesRepositoryImpl
import com.santimattius.template.data.repositories.TvShowsRepositoryImpl
import com.santimattius.template.domain.repositories.MovieRepository
import com.santimattius.template.domain.repositories.TvShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideMovieRepository(
        movieLocalDataSource: MovieLocalDataSource,
        movieRemoteDataSource: MovieRemoteDataSource,
    ): MovieRepository = MoviesRepositoryImpl(
        movieRemoteDataSource = movieRemoteDataSource,
        movieLocalDataSource = movieLocalDataSource
    )

    @Provides
    fun provideTvShowRepository(
        tvShowLocalDataSource: TvShowLocalDataSource,
        tvShowRemoteDataSource: TvShowRemoteDataSource,
    ): TvShowsRepository = TvShowsRepositoryImpl(tvShowLocalDataSource, tvShowRemoteDataSource)

}