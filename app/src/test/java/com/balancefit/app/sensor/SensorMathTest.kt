package com.balancefit.app.sensor

import org.junit.Assert.assertEquals
import org.junit.Test

class SensorMathTest {

    @Test
    fun flatSurface_returnsNearZero() {
        val angle = calculateTiltAngle(ax = 0f, ay = 0f, az = 9.8f)
        assertEquals(0f, angle, 0.3f)
    }

    @Test
    fun forwardTilt_returnsPositiveAngle() {
        val angle = calculateTiltAngle(ax = 0f, ay = 2.5f, az = 9.4f)
        assertEquals(14.8f, angle, 1.5f)
    }

    @Test
    fun smoothing_appliesEmaWeight() {
        val smoothed = applyEma(previous = 10f, current = 0f, alpha = 0.2f)
        assertEquals(8f, smoothed, 0.01f)
    }
}
