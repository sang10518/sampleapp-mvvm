package com.swc.sampleapp_mvvm.di

import com.swc.sampleapp_mvvm.network.RetrofitClient
import com.swc.sampleapp_mvvm.network.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideWeatherApiService(): WeatherApiService {
        return RetrofitClient.getInstance().create(WeatherApiService::class.java)
    }
}