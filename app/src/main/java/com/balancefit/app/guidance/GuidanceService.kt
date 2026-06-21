package com.balancefit.app.guidance

import com.balancefit.app.AppError

interface GuidanceService {
    fun startGuidance(onFeedback: (String) -> Unit, onError: (AppError) -> Unit)
    fun stopGuidance()
}
