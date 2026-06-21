package com.balancefit.app.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class AppRootTest {

    @Test
    fun appTitle_returnsBalanceFit() {
        assertEquals("Balance Fit", appTitle())
    }
}
