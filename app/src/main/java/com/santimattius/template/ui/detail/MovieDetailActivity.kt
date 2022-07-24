package com.santimattius.template.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.santimattius.template.ui.components.content
import org.koin.androidx.viewmodel.ext.android.stateViewModel

@ExperimentalLifecycleComposeApi
class MovieDetailActivity : ComponentActivity() {

    private val viewModel: MovieDetailViewModel by stateViewModel(
        state = { intent.asBundle() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content {
            MovieDetailRoute(
                viewModel = viewModel,
                navigateUp = { onBackPressed() }
            )
        }
    }

    private fun Intent.asBundle(): Bundle {
        val id = this.data?.lastPathSegment?.toInt()
        return Bundle().apply {
            if (id != null) {
                putInt("id", id)
            }
        }
    }
}