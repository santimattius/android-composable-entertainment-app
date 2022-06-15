package com.santimattius.template.ui.home.robolectric

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.santimattius.template.data.dtoToDomain
import com.santimattius.template.domain.repositories.MovieRepository
import com.santimattius.template.ui.home.HomeFragment
import com.santimattius.template.ui.home.MainActivity
import com.santimattius.template.ui.home.components.viewholders.MovieViewHolder
import com.santimattius.template.ui.home.viewmodels.FakeMovieRepository
import com.santimattius.template.utils.KoinRule
import com.santimattius.template.utils.MainCoroutinesTestRule
import com.santimattius.template.utils.TheMovieDBMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.R])
class MainActivityRobolectricTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = MainCoroutinesTestRule()

    @get:Rule
    val koinRule = KoinRule.robolectric(module = module {
        single<MovieRepository> {
            FakeMovieRepository(answers = { TheMovieDBMother.movies().dtoToDomain() })
        }
    })

    @Test
    fun `verify first movie is spider-man`() {
        // Launch our Activity
        val activityController = Robolectric.buildActivity(
            MainActivity::class.java
        ).setup()

        val activity = activityController.get()

        val fragmentContainer = activity.viewBinding.fragmentContainerView

        val fragment = fragmentContainer.getFragment<HomeFragment>()

        val recyclerView = fragment.viewBinding.gridOfMovies

        // Pull out the first ViewHolder
        val viewHolder = recyclerView
            .findViewHolderForAdapterPosition(0)

        val imageView = (viewHolder as MovieViewHolder).viewBinding.imageMovie

        assertThat(
            imageView.contentDescription,
            equalTo("Spider-Man: No Way Home")
        )
    }
}