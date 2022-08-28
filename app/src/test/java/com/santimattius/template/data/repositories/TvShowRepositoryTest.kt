package com.santimattius.template.data.repositories

import com.santimattius.template.data.datasources.TvShowLocalDataSource
import com.santimattius.template.data.datasources.TvShowRemoteDataSource
import com.santimattius.template.data.datasources.impl.MovieNoExists
import com.santimattius.template.data.datasources.impl.TvShowNoExists
import com.santimattius.template.data.datasources.local.TvShowEntityMother
import com.santimattius.template.data.datasources.remote.TheMovieDBMother
import com.santimattius.template.data.dtoToDomain
import com.santimattius.template.data.entityToDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNull.nullValue
import org.junit.Test
import kotlin.random.Random

@ExperimentalCoroutinesApi
class TvShowRepositoryTest {

    private val remoteDataSource = mockk<TvShowRemoteDataSource>()
    private val localDataSource = mockk<TvShowLocalDataSource>(relaxed = true)
    private val repository = TvShowsRepositoryImpl(localDataSource, remoteDataSource)

    private val tvShows = TheMovieDBMother.tvShows()
    private val tvShowsEntities = TvShowEntityMother.tvShows()

    @Test
    fun `get all tv show from cache`() {

        coEvery {
            localDataSource.getAll()
        } returns tvShowsEntities

        runTest {
            val result = repository.getPopular()
            assertThat(result.isNotEmpty(), equalTo(true))
        }

        coVerify { localDataSource.getAll() }
    }

    @Test
    fun `get all tv show when empty cache`() {

        coEvery {
            localDataSource.getAll()
        } returns emptyList()

        coEvery {
            remoteDataSource.getAll()
        } returns Result.success(tvShows)

        runTest {
            val result = repository.getPopular()
            assertThat(result.isNotEmpty(), equalTo(true))
        }

        coVerify { localDataSource.getAll() }
        coVerify { remoteDataSource.getAll() }
        coVerify { localDataSource.save(any()) }
    }

    @Test
    fun `get all tv show when empty cache and service fail`() {

        coEvery {
            localDataSource.getAll()
        } returns emptyList()

        coEvery {
            remoteDataSource.getAll()
        } returns Result.failure(Throwable())

        runTest {
            val result = repository.getPopular()
            assertThat(result.isEmpty(), equalTo(true))
        }

        coVerify { localDataSource.getAll() }
        coVerify { remoteDataSource.getAll() }
        coVerify { localDataSource.save(any()) }
    }

    @Test
    fun `fetch tv shows and save with response`() {

        coEvery {
            remoteDataSource.getAll()
        } returns Result.success(tvShows)

        runTest {
            val result = repository.fetchPopular()
            assertThat(result.isSuccess, equalTo(true))
        }

        coVerify { remoteDataSource.getAll() }
        coVerify { localDataSource.save(any()) }
    }

    @Test
    fun `fetch tv shows and save without response`() {

        coEvery {
            remoteDataSource.getAll()
        } returns Result.failure(Throwable())

        runTest {
            val result = repository.fetchPopular()
            assertThat(result.isSuccess, equalTo(true))
            assertThat(result.getOrNull(), equalTo(emptyList()))
        }

        coVerify { remoteDataSource.getAll() }
        coVerify(exactly = 0) { localDataSource.save(any()) }
    }

    @Test
    fun `find tv show from local`() {
        val tvShow = TvShowEntityMother.tvShow()
        coEvery {
            localDataSource.find(tvShow.id)
        } returns Result.success(tvShow)

        runTest {
            val tvShowSearched = repository.find(tvShow.id)
            assertThat(tvShowSearched, equalTo(tvShow.entityToDomain()))
        }

        coVerify { localDataSource.find(tvShow.id) }
    }

    @Test
    fun `find tv show when not local exists and call service`() {
        val tvShow = tvShows.first()
        coEvery { localDataSource.find(tvShow.id) } returns Result.failure(MovieNoExists())
        coEvery { remoteDataSource.find(tvShow.id) } returns Result.success(tvShow)

        runTest {
            val movieSearched = repository.find(tvShow.id)
            assertThat(movieSearched, IsEqual(tvShow.dtoToDomain()))
        }

        coVerify { localDataSource.find(tvShow.id) }
        coVerify { remoteDataSource.find(tvShow.id) }
    }

    @Test
    fun `find movie when not local exists and call service return error`() {
        val tvShowId = Random.nextInt()

        coEvery { localDataSource.find(tvShowId) } returns Result.failure(TvShowNoExists())
        coEvery { remoteDataSource.find(tvShowId) } returns Result.failure(Throwable())

        runTest {
            val tvShowSearched = repository.find(tvShowId)
            assertThat(tvShowSearched, nullValue())
        }

        coVerify { localDataSource.find(tvShowId) }
        coVerify { remoteDataSource.find(tvShowId) }
    }
}