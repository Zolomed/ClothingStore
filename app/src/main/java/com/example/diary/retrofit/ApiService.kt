package com.example.diary.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("current.json")
    fun getWeather(@Query("key") api_key: String, @Query("q") city_name: String, @Query("aqi") aqi: String): Call<Weather>
}