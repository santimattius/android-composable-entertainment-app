package com.santimattius.template.core.presentation

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.santimattius.template.core.presentation.AlertDialog.Companion.alertDialog

/**
 * open link in to browser
 */
fun Fragment.openLink(url: String) {
    if (!url.startsWith("http://") && !url.startsWith("https://")) return
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
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