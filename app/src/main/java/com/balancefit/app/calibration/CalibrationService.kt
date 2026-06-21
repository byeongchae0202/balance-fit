package com.balancefit.app.calibration

import com.balancefit.app.AppError

interface CalibrationService {
    fun startCalibration(onComplete: () -> Unit, onError: (AppError) -> Unit)
    fun reset()
    val isCalibrated: Boolean
}
