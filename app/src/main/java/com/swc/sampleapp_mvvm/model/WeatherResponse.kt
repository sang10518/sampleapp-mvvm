package com.swc.sampleapp_mvvm.model

data class WeatherResponse(
    val name: String,
    val main: Main
) {
    data class Main(
        val temp: Float
    )
}
