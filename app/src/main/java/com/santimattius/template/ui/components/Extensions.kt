package com.santimattius.template.ui.components

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.santimattius.template.ui.components.AlertDialog.Companion.alertDialog
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
 * show AlertDialog in Fragment
 */
fun Fragment.showDialog(
    message: String,
    positiveAction: DialogAction,
    negativeAction: DialogAction,
) = requireContext().showDialog(message, positiveAction, negativeAction)

/**
 * show AlertDialog from context
 */
fun Context.showDialog(
    message: String,
    positiveAction: DialogAction,
    negativeAction: DialogAction,
) = alertDialog(context = this) {
    message(message)
    positiveAction(positiveAction)
    negativeAction(negativeAction)
}

/**
 * image view extension using Glide
 */
fun ImageView.load(url: String, onComplete: () -> Unit = {}) {
    Glide.with(context)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean,
            ): Boolean {
                onComplete()
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean,
            ): Boolean {
                onComplete()
                return false
            }
        }).into(this)
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