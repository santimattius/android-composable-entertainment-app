package com.santimattius.template.domain.usecases.tvshows

import com.santimattius.template.domain.repositories.TvShowsRepository
import com.santimattius.template.utils.MainCoroutinesTestRule
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchPopularTvShowsTest {

    @get:Rule
    val coroutineRule = MainCoroutinesTestRule()

    @Test
    fun `verify call repository`() {
        val repository = mockk<TvShowsRepository>(relaxed = true)
        val fetchPopularTvShows = FetchPopularTvShows(repository)
        runTest {
            fetchPopularTvShows.invoke()
        }
        coVerify { repository.fetchPopular() }
    }
}