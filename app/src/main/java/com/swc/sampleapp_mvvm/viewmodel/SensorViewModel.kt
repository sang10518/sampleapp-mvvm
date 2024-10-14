package com.swc.sampleapp_mvvm.viewmodel


import android.hardware.SensorEvent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swc.sampleapp_mvvm.model.Post
import com.swc.sampleapp_mvvm.model.PostResponse
import com.swc.sampleapp_mvvm.model.WeatherResponse
import com.swc.sampleapp_mvvm.repository.PostRepository
import com.swc.sampleapp_mvvm.repository.SensorRepository
import com.swc.sampleapp_mvvm.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val sensorRepository: SensorRepository
) : ViewModel() {

    private val _sensorData = MutableStateFlow<SensorEvent?>(null)
    val sensorData: StateFlow<SensorEvent?> = _sensorData

    private var isSensorActive = false

    fun toggleAccelerometerUpdates() {
        if (isSensorActive) {
            sensorRepository.stopAccelerometerUpdates()
        } else {
            sensorRepository.startAccelerometerUpdates()
            viewModelScope.launch {
                sensorRepository.getAccelerometerUpdates()
                    .collect { event ->
                        _sensorData.value = event
                    }
            }
        }
        isSensorActive = !isSensorActive
    }
}

