package com.balancefit.app

sealed class AppError(val code: String) {
    object SensorUnavailable : AppError("SENSOR_UNAVAILABLE")
    object PermissionDenied : AppError("PERMISSION_DENIED")
}
