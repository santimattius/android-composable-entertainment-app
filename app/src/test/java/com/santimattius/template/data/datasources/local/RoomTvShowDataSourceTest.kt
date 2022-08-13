package com.santimattius.template.data.datasources.local

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.santimattius.template.data.client.database.AppDataBase
import com.santimattius.template.data.datasources.impl.RoomTvShowLocalDataSource
import com.santimattius.template.utils.MainCoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException
import kotlin.random.Random

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.R])
class RoomTvShowDataSourceTest {

    @get:Rule
    val coroutinesTestRule = MainCoroutinesTestRule()

    private lateinit var db: AppDataBase
    private lateinit var dataSource: RoomTvShowLocalDataSource

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dataSource = RoomTvShowLocalDataSource(db.tvShowDao(), coroutinesTestRule.testDispatcher)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun `validate that the tv show was saved correctly`() =
        runTest(coroutinesTestRule.testDispatcher) {
            val tvShows = TvShowEntityMother.tvShows()
            val randomTvShow = tvShows.random()

            val result = dataSource.save(tvShows)
            val findResult = dataSource.find(randomTvShow.id)

            assertThat(result.isSuccess, IsEqual(true))
            assertThat(findResult.isSuccess, IsEqual(true))
        }

    @Test
    fun `validate that there is stored data`() = runTest(coroutinesTestRule.testDispatcher) {
        val tvShows = TvShowEntityMother.tvShows()
        dataSource.save(tvShows)
        val result = dataSource.isEmpty()
        assertThat(result, IsEqual(false))
    }

    @Test
    fun `validate that there no stored data`() = runTest(coroutinesTestRule.testDispatcher) {
        val result = dataSource.isEmpty()
        assertThat(result, IsEqual(true))
    }

    @Test
    fun `validate search by id when tv show is stored`() =
        runTest(coroutinesTestRule.testDispatcher) {
            val tvShows = TvShowEntityMother.tvShows()
            val randomTvShow = tvShows.random()
            dataSource.save(tvShows)
            val result = dataSource.find(randomTvShow.id)
            assertThat(result.isSuccess, IsEqual(true))
        }

    @Test
    fun `validate search by id when tv show is no stored`() =
        runTest(coroutinesTestRule.testDispatcher) {
            val result = dataSource.find(Random.nextInt())
            assertThat(result.isFailure, IsEqual(true))
        }

    @Test
    fun `validate delete when movie is stored`() = runTest(coroutinesTestRule.testDispatcher) {
        val tvShows = TvShowEntityMother.tvShows()
        val randomTvShow = tvShows.random()

        dataSource.save(tvShows)
        dataSource.delete(randomTvShow)

        val result = dataSource.find(randomTvShow.id)
        assertThat(result.isSuccess, IsEqual(false))
    }

    @Test
    fun `validate update when movie is stored`() = runTest(coroutinesTestRule.testDispatcher) {
        val tvShows = TvShowEntityMother.tvShows()
        val randomTvShow = tvShows.random()
        dataSource.save(tvShows)
        val movieUpdated = randomTvShow.copy(title = "Title Updated")

        dataSource.update(movieUpdated)

        val result = dataSource.find(movieUpdated.id)
        assertThat(result.getOrNull(), IsNot(IsEqual(randomTvShow)))
    }
}