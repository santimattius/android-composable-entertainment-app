package com.santimattius.template.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.santimattius.template.R

@Composable
fun ErrorDialog(
    message: String,
    positiveAction: AlertDialog,
    negativeAction: AlertDialog,
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = positiveAction.action) {
                Text(text = positiveAction.text)
            }
        },
        dismissButton = {
            TextButton(onClick = negativeAction.action) {
                Text(text = negativeAction.text)
            }
        },
        text = { Text(text = message) }
    )
}

@Preview(showSystemUi = true)
@Composable
fun ErrorDialogPreview() {
    ErrorDialog(
        message = stringResource(
            id = R.string.message_loading_error
        ),
        positiveAction = AlertDialog(text = stringResource(R.string.button_text_positive_error)) {

        },
        negativeAction = AlertDialog(text = stringResource(R.string.button_text_negative_error)) {

        }
    )
}