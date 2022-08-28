package com.santimattius.template.di

import com.santimattius.template.domain.repositories.MovieRepository
import com.santimattius.template.domain.repositories.TvShowsRepository
import com.santimattius.template.domain.usecases.movies.FetchPopularMovies
import com.santimattius.template.domain.usecases.movies.FindMovie
import com.santimattius.template.domain.usecases.movies.GetPopularMovies
import com.santimattius.template.domain.usecases.tvshows.FetchPopularTvShows
import com.santimattius.template.domain.usecases.tvshows.FindTvShow
import com.santimattius.template.domain.usecases.tvshows.GetPopularTvShows
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UiModule {

    @Provides
    @ViewModelScoped
    fun provideFetchPopularMovies(repository: MovieRepository) =
        FetchPopularMovies(repository = repository)

    @Provides
    @ViewModelScoped
    fun provideGetPopularMovies(repository: MovieRepository) =
        GetPopularMovies(repository = repository)

    @Provides
    @ViewModelScoped
    fun provideFindMovie(repository: MovieRepository) = FindMovie(repository = repository)

    @Provides
    @ViewModelScoped
    fun provideFetchPopularTvShows(repository: TvShowsRepository) =
        FetchPopularTvShows(repository = repository)

    @Provides
    @ViewModelScoped
    fun provideGetPopularTvShows(repository: TvShowsRepository) =
        GetPopularTvShows(repository = repository)

    @Provides
    @ViewModelScoped
    fun provideFindTvShow(repository: TvShowsRepository) = FindTvShow(repository = repository)

}