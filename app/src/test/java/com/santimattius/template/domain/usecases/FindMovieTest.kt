package com.santimattius.template.domain.usecases

import com.santimattius.template.data.datasources.local.MovieEntityMother
import com.santimattius.template.data.entityToDomain
import com.santimattius.template.data.FakeMovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Test
import kotlin.random.Random

@ExperimentalCoroutinesApi
class FindMovieTest {

    @Test
    fun `find movie by when movie exists`() {
        val movie = MovieEntityMother.movie().entityToDomain()
        val repository = FakeMovieRepository(answers = { listOf(movie) })
        val useCase = FindMovie(repository)
        runTest {
            MatcherAssert.assertThat(useCase(id = movie.id), IsEqual(movie))
        }
    }

    @Test
    fun `find movie by when movie not exists`() {
        val repository = FakeMovieRepository(answers = { emptyList() })
        val useCase = FindMovie(repository)
        runTest {
            MatcherAssert.assertThat(useCase(id = Random.nextInt()), nullValue())
        }
    }
}