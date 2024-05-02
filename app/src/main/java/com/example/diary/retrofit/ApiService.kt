package com.example.diary.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
//    @GET("current.json")
//    fun getWeather(@Query("key") api_key: String, @Query("q") city_name: String, @Query("aqi") aqi: String): Call<Weather>

    @GET("current.json")
    fun getWeather(@QueryMap options: Map<String, String>): Call<Weather>
}