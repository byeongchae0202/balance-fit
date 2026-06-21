package com.balancefit.app.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.balancefit.app.MainActivity
import org.junit.Rule
import org.junit.Test

class AppRootComposeTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appRoot_displaysBalanceFit() {
        composeRule.onNodeWithText("Balance Fit").assertIsDisplayed()
    }
}
