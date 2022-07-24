package com.santimattius.template.ui.home.robolectric

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ActivityScenario
import com.santimattius.template.data.dtoToDomain
import com.santimattius.template.domain.repositories.MovieRepository
import com.santimattius.template.ui.home.MainActivity
import com.santimattius.template.ui.home.viewmodels.FakeMovieRepository
import com.santimattius.template.utils.KoinRule
import com.santimattius.template.utils.MainCoroutinesTestRule
import com.santimattius.template.utils.TheMovieDBMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.R],
    instrumentedPackages = ["androidx.loader.content"],
)
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

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `verify first movie is spider-man`() {
        // Launch our Activity
        ActivityScenario.launch(MainActivity::class.java).use {
            composeTestRule
                .onNodeWithTag("Spider-Man: No Way Home")
                .assertIsDisplayed()
        }
    }
}