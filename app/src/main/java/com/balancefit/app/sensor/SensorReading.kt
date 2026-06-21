package com.balancefit.app.sensor

data class SensorReading(
    val angle: Float,
    val x: Float,
    val y: Float,
    val z: Float,
    val timestampMillis: Long
)
