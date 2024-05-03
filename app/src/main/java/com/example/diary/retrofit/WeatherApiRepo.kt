package com.example.diary.retrofit

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiRepo {

    private val retrofit = Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/").addConverterFactory(
        GsonConverterFactory.create()).build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun getDataFromApi(city:String, rvWeather: RecyclerView, search: EditText, callback: (Weather?) -> Unit){
        val data: MutableMap<String, String> = HashMap()
        data["key"] = "8c34f5691fcb4e6e9e1180730241704"
        data["aqi"] = "no"
        data["q"] = city
        apiService.getWeather(data).enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful) {
                    if (response.code() == 200){
                        val weatherResponse = response.body()
                        callback(weatherResponse)
                        rvWeather.visibility = View.VISIBLE
                        search.text.clear()
                    }
                } else {
                    Log.e("ApiError", "Request failed: " + response.code())
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.e("ApiError", "Request failed: ${t.message}")
            }
        })
    }
}