package com.swc.sampleapp_mvvm.di

import android.content.Context
import android.hardware.SensorManager
import com.swc.sampleapp_mvvm.network.PostApiService
import com.swc.sampleapp_mvvm.network.RetrofitClient
import com.swc.sampleapp_mvvm.network.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Provides
    fun provideSensorManager(@ApplicationContext context: Context): SensorManager {
        return context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
}

