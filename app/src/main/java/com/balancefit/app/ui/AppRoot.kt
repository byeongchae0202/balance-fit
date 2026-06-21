package com.balancefit.app.ui

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

fun appTitle(): String = "Balance Fit"

@Composable
fun AppRoot() {
    MaterialTheme {
        Surface(modifier = Modifier.systemBarsPadding()) {
            Text(text = appTitle())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppRootPreview() {
    AppRoot()
}
