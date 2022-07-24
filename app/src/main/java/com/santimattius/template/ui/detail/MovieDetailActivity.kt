package com.santimattius.template.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.santimattius.template.ui.theme.AndroidComposableEntertainmentAppTheme
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
                onBackPressed = { onBackPressed() }
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

fun ComponentActivity.content(content: @Composable () -> Unit) {
    setContent {
        AndroidComposableEntertainmentAppTheme {
            Surface(color = MaterialTheme.colors.background) {
                content()
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