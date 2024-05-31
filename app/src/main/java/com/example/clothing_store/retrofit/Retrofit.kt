package com.example.clothing_store.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private val weatherRetrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherService: WeatherApiService = weatherRetrofit.create(WeatherApiService::class.java)

    private val myRetrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl("http://192.168.1.78:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myService: MyApiService = myRetrofit.create(MyApiService::class.java)
}