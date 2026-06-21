package com.balancefit.app.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class BalanceUiLogicTest {

    @Test
    fun angleWithinOne_returnsPerfect() {
        assertEquals("완벽", balanceStatusLabel(0.8f))
    }

    @Test
    fun angleWithinFive_returnsCareful() {
        assertEquals("약간 기울어짐", balanceStatusLabel(3.2f))
    }

    @Test
    fun angleOverFive_returnsDanger() {
        assertEquals("심하게 기울어짐", balanceStatusLabel(-6.0f))
    }
}
