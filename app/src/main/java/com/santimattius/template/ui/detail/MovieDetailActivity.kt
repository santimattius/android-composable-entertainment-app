package com.santimattius.template.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.template.ui.home.Center
import com.santimattius.template.ui.theme.AndroidComposableEntertainmentAppTheme
import org.koin.androidx.viewmodel.ext.android.stateViewModel

@ExperimentalLifecycleComposeApi
class MovieDetailActivity : ComponentActivity() {

    private val viewModel: MovieDetailViewModel by stateViewModel(
        state = {
            val id = intent.data?.lastPathSegment?.toInt()
            if (id == null) {
                Bundle()
            } else {
                Bundle().apply {
                    putInt("id", id)
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposableEntertainmentAppTheme {
                Surface(color = MaterialTheme.colors.background) {
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
                    ) {
                        val state by viewModel.state.collectAsStateWithLifecycle()
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
                }
            }
        }
    }
}

@Composable
fun ArrowBackIcon(onUpClick: () -> Unit) {
    IconButton(onClick = onUpClick) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null
        )
    }
}