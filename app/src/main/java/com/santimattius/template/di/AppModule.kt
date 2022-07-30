package com.santimattius.template.di

import android.app.Application
import com.santimattius.template.BuildConfig
import com.santimattius.template.data.client.database.AppDataBase
import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.client.network.service
import com.santimattius.template.data.datasources.LocalDataSource
import com.santimattius.template.data.datasources.RemoteDataSource
import com.santimattius.template.data.datasources.implementation.MovieDataSource
import com.santimattius.template.data.datasources.implementation.RoomDataSource
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
    fun provideLocalDataSource(db: AppDataBase): LocalDataSource =
        RoomDataSource(dao = db.dao(), dispatcher = Dispatchers.IO)


    @Provides
    fun provideRemoteDataSource(client: TheMovieDBService): RemoteDataSource =
        MovieDataSource(service = client)
}