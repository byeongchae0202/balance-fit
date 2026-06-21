package com.balancefit.app.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.balancefit.app.AppError

class AndroidSensorDataSource(
    context: Context
) : SensorDataSource, SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private var onDataCallback: ((SensorReading) -> Unit)? = null
    private var onErrorCallback: ((AppError) -> Unit)? = null
    private var isRegistered = false

    private var ax = 0f
    private var ay = 0f
    private var az = 9.8f
    private var smoothedAngle = 0f

    override fun isAvailable(): Boolean {
        return accelerometer != null && gyroscope != null
    }

    override fun startStreaming(onData: (SensorReading) -> Unit, onError: (AppError) -> Unit) {
        onDataCallback = onData
        onErrorCallback = onError
        if (!isAvailable()) {
            onError(AppError.SensorUnavailable)
            return
        }
        if (!isRegistered) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME)
            isRegistered = true
        }
    }

    override fun stopStreaming() {
        if (isRegistered) {
            sensorManager.unregisterListener(this)
            isRegistered = false
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                ax = event.values[0]
                ay = event.values[1]
                az = event.values[2]
                val rawAngle = calculateTiltAngle(ax = ax, ay = ay, az = az)
                smoothedAngle = applyEma(previous = smoothedAngle, current = rawAngle, alpha = 0.2f)
                onDataCallback?.invoke(
                    SensorReading(
                        angle = smoothedAngle,
                        x = ax,
                        y = ay,
                        z = az,
                        timestampMillis = System.currentTimeMillis()
                    )
                )
            }
            Sensor.TYPE_GYROSCOPE -> {
                // 현재 버전은 안정성 확인용 등록만 수행하고 각도 계산은 가속도계를 사용한다.
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}
