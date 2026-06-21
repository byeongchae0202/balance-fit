package com.balancefit.app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

fun appTitle(): String = "Balance Fit"

@Composable
fun AppRoot() {
    MaterialTheme {
        Surface {
            Text(text = appTitle())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppRootPreview() {
    AppRoot()
}
