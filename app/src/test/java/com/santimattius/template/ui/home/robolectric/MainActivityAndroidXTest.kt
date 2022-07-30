package com.santimattius.template.ui.home.robolectric


import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.santimattius.template.ui.MainActivity
import com.santimattius.template.utils.MainCoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalAnimationApi
@ExperimentalLifecycleComposeApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.R],
    instrumentedPackages = ["androidx.loader.content"]
)
class MainActivityAndroidXTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = MainCoroutinesTestRule()

    @get:Rule
    val composeTestRule = createComposeRule()

//    @get:Rule
//    val koinRule = KoinRule.androidx(module = module {
//        single<MovieRepository> {
//            FakeMovieRepository(answers = { TheMovieDBMother.movies().dtoToDomain() })
//        }
//    })

    @Test
    fun `verify first movie is spider-man`() {

        ActivityScenario.launch(MainActivity::class.java).use {
            composeTestRule
                .onNodeWithTag("Spider-Man: No Way Home")
                .assertIsDisplayed()
        }
    }
}