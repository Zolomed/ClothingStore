package com.example.diary.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("current.json")
    fun getWeather(@Query("key") api_key: String = "8c34f5691fcb4e6e9e1180730241704", @Query("q") city_name: String, @Query("aqi") aqi: String = "no"): Call<Weather>
}