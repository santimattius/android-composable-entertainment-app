package com.santimattius.template.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.template.ui.home.Center

@ExperimentalLifecycleComposeApi
@Composable
fun MovieDetailRoute(
    viewModel: MovieDetailViewModel,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    ArrowBackIcon {
                        onBackPressed()
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            val state by viewModel.state.collectAsStateWithLifecycle()
            MovieDetailContent(state)
        }
    }
}

@Composable
private fun MovieDetailContent(
    state: MovieDetailState,
) {
    when {
        state.isLoading -> {
            Center {
                CircularProgressIndicator()
            }
        }
        state.hasError -> {
            Center {
                Text("Error")
            }
        }
        else -> {
            Center {
                Text("Idem: ${state.data?.title}")
            }
        }
    }
}