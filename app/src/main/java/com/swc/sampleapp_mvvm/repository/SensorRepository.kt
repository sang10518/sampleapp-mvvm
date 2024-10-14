package com.swc.sampleapp_mvvm.repository


import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.swc.sampleapp_mvvm.network.PostApiService
import com.swc.sampleapp_mvvm.network.WeatherApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorRepository @Inject constructor(
    private val sensorManager: SensorManager
) {
    private val accelerometerFlow = MutableSharedFlow<SensorEvent>()
    private var accelerometerListener: SensorEventListener? = null

    fun startAccelerometerUpdates() {
        if (accelerometerListener == null) {
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            accelerometerListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    // Emit the accelerometer data to the flow
                    accelerometerFlow.tryEmit(event)
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    // Handle accuracy changes if needed
                }
            }
            sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopAccelerometerUpdates() {
        accelerometerListener?.let {
            sensorManager.unregisterListener(it)
            accelerometerListener = null
        }
    }

    fun getAccelerometerUpdates(): Flow<SensorEvent> {
        return accelerometerFlow
    }
}
