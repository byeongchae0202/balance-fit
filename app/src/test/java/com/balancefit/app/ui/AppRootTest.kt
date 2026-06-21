package com.balancefit.app.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class AppRootTest {

    @Test
    fun appTitle_returnsDashboardTitle() {
        assertEquals("Balance Fit 대시보드", appTitle())
    }
}
