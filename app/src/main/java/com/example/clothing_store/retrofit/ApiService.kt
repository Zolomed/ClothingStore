package com.example.clothing_store.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("current.json")
    fun getWeather(@QueryMap options: Map<String, String>): Call<Weather>
}