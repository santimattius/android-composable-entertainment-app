package com.santimattius.template.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.santimattius.template.ui.theme.AndroidComposableEntertainmentAppTheme

/**
 * open link in to browser
 */
fun Context.navigateToDeeplink(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    this.startActivity(browserIntent)
}

/**
 * Compose application style
 *
 * @param content, composable content.
 */
fun ComponentActivity.content(content: @Composable () -> Unit) {
    setContent {
        AndroidComposableEntertainmentAppTheme {
            Surface(color = MaterialTheme.colors.background) {
                content()
            }
        }
    }
}