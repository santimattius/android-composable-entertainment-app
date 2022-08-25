package com.santimattius.template.domain.usecases.movies

import com.santimattius.template.data.FakeMovieRepository
import com.santimattius.template.domain.usecases.movies.GetPopularMovies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test

@ExperimentalCoroutinesApi
class GetPopularMoviesTest {

    @Test
    fun `invoke get popular movies use case`() {
        val repository = FakeMovieRepository(answers = { emptyList() })
        val useCase = GetPopularMovies(repository)
        runTest {
            assertThat(useCase().isEmpty(), IsEqual(true))
        }
    }
}