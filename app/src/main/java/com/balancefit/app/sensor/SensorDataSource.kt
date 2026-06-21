package com.balancefit.app.sensor

import com.balancefit.app.AppError

interface SensorDataSource {
    fun isAvailable(): Boolean
    fun startStreaming(onData: (FloatArray) -> Unit, onError: (AppError) -> Unit)
    fun stopStreaming()
}
