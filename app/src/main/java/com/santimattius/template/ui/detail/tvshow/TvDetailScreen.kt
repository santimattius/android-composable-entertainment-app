package com.santimattius.template.ui.detail.tvshow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.template.ui.detail.DetailScaffold

@ExperimentalLifecycleComposeApi
@Composable
fun TvShowDetailRoute(
    viewModel: TvShowDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailScaffold(state)
}
