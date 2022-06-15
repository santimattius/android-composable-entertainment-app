package com.santimattius.template.core.presentation

import android.content.Context
import androidx.appcompat.app.AlertDialog as AndroidAlertDialog

/**
 * Kotlin Dialog Action
 */
data class DialogAction(val text: String, val action: () -> Unit)

/**
 * Custom AlertDialog DSL
 */
class AlertDialog private constructor(context: Context) {

    private val builder = AndroidAlertDialog.Builder(context)

    fun message(text: String) = apply {
        builder.setMessage(text)
    }

    fun positiveAction(action: DialogAction) = apply {
        builder.setPositiveButton(action.text) { dialog, _ ->
            dialog.dismiss()
            action.action.invoke()
        }
    }

    fun negativeAction(action: DialogAction) = apply {
        builder.setNegativeButton(action.text) { dialog, _ ->
            dialog.dismiss()
            action.action.invoke()
        }
    }

    private fun show() {
        builder.create().show()
    }

    companion object {

        @Suppress("MemberNameEqualsClassName")
        fun alertDialog(context: Context, builder: AlertDialog.() -> AlertDialog) {
            val dialogCreated = AlertDialog(context).builder()
            dialogCreated.show()
        }
    }
}