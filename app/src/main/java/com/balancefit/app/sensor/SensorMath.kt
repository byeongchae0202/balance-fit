package com.balancefit.app.sensor

import kotlin.math.atan2
import kotlin.math.sqrt

fun calculateTiltAngle(ax: Float, ay: Float, az: Float): Float {
    if (az == 0f) return 0f
    val roll = atan2(ax.toDouble(), az.toDouble())
    val pitch = atan2(ay.toDouble(), az.toDouble())
    return Math.toDegrees(sqrt(roll * roll + pitch * pitch)).toFloat()
}

fun applyEma(previous: Float, current: Float, alpha: Float): Float {
    return (alpha * current) + ((1f - alpha) * previous)
}
