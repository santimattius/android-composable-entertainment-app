package com.santimattius.template

import com.santimattius.template.data.FakeMovieRepository
import com.santimattius.template.data.FakeTvShowRepository
import com.santimattius.template.di.DataModule
import com.santimattius.template.domain.repositories.MovieRepository
import com.santimattius.template.domain.repositories.TvShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
class FakeDataModule {

    @Provides
    fun bindMovieRepository(): MovieRepository {
        return FakeMovieRepository()
    }

    @Provides
    fun bindTvShowRepository(): TvShowsRepository {
        return FakeTvShowRepository()
    }
}