package com.santimattius.template.ui.components

/**
 * Kotlin Dialog Action
 */
data class AlertDialog(val text: String, val action: () -> Unit)