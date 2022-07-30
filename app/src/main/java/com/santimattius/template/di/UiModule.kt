package com.santimattius.template.di

import com.santimattius.template.domain.repositories.MovieRepository
import com.santimattius.template.domain.usecases.FetchPopularMovies
import com.santimattius.template.domain.usecases.FindMovie
import com.santimattius.template.domain.usecases.GetPopularMovies
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
}