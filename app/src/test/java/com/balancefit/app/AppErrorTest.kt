package com.balancefit.app

import org.junit.Assert.assertEquals
import org.junit.Test

class AppErrorTest {

    @Test
    fun sensorUnavailable_hasCorrectCode() {
        assertEquals("SENSOR_UNAVAILABLE", AppError.SensorUnavailable.code)
    }

    @Test
    fun permissionDenied_hasCorrectCode() {
        assertEquals("PERMISSION_DENIED", AppError.PermissionDenied.code)
    }
}
