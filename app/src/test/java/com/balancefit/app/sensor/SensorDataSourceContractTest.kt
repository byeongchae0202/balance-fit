package com.balancefit.app.sensor

import org.junit.Assert.assertEquals
import org.junit.Test

class SensorDataSourceContractTest {

    @Test
    fun androidSensorDataSource_typeExists() {
        assertEquals("AndroidSensorDataSource", AndroidSensorDataSource::class.java.simpleName)
    }
}
