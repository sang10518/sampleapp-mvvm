package com.swc.sampleapp_mvvm.view.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.swc.sampleapp_mvvm.viewmodel.PostViewModel
import com.swc.sampleapp_mvvm.viewmodel.WeatherViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.swc.sampleapp_mvvm.model.Post
import com.swc.sampleapp_mvvm.viewmodel.SensorViewModel

@Composable
fun SensorScreen(sensorViewModel: SensorViewModel) {
    val sensorData by sensorViewModel.sensorData.collectAsState()

    Column {
        sensorData?.let { event ->
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            Text(text = "Accelerometer X: $x, Y: $y, Z: $z")
        }

        var isSensorActive by remember { mutableStateOf(false) }

        Button(onClick = {
            sensorViewModel.toggleAccelerometerUpdates()
            isSensorActive = !isSensorActive
        }) {
            Text(if (isSensorActive) "Stop Sensor" else "Start Sensor")
        }
    }
}

