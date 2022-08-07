package com.santimattius.template.di

import android.app.Application
import com.santimattius.template.BuildConfig
import com.santimattius.template.data.client.database.AppDataBase
import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.client.network.service
import com.santimattius.template.data.datasources.MovieLocalDataSource
import com.santimattius.template.data.datasources.MovieRemoteDataSource
import com.santimattius.template.data.datasources.TvShowLocalDataSource
import com.santimattius.template.data.datasources.TvShowRemoteDataSource
import com.santimattius.template.data.datasources.impl.RoomMovieDataSource
import com.santimattius.template.data.datasources.impl.RoomTvShowLocalDataSource
import com.santimattius.template.data.datasources.impl.TMDBMovieDataSource
import com.santimattius.template.data.datasources.impl.TMDBTvShowRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideDataBase(app: Application): AppDataBase = AppDataBase.get(context = app)

    @Provides
    fun provideService(@Named("apiKey") apiKey: String): TheMovieDBService = service(key = apiKey)

    @Provides
    fun provideMovieLocalDataSource(db: AppDataBase): MovieLocalDataSource =
        RoomMovieDataSource(dao = db.movieDao(), dispatcher = Dispatchers.IO)


    @Provides
    fun provideMovieRemoteDataSource(client: TheMovieDBService): MovieRemoteDataSource =
        TMDBMovieDataSource(service = client)

    @Provides
    fun provideTvShowLocalDataSource(db: AppDataBase): TvShowLocalDataSource =
        RoomTvShowLocalDataSource(dao = db.tvShowDao(), dispatcher = Dispatchers.IO)


    @Provides
    fun provideTvShowRemoteDataSource(client: TheMovieDBService): TvShowRemoteDataSource =
        TMDBTvShowRemoteDataSource(service = client)
}