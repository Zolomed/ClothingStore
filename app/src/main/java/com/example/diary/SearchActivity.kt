package com.example.diary

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.diary.retrofit.ApiService
import com.example.diary.retrofit.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var search: EditText
    private lateinit var clearButton: Button
    private lateinit var backButton: Button
    private lateinit var weatherButton: Button

    private val retrofit = Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        search = findViewById(R.id.search)
        clearButton = findViewById(R.id.clear_button)
        backButton = findViewById(R.id.back_button)
        weatherButton = findViewById(R.id.weather_button)

        val apiService = retrofit.create(ApiService::class.java)

        if (savedInstanceState != null) {
            val searchText = savedInstanceState.getString(SEARCH_TEXT_KEY)
            search.setText(searchText)
        }

        search.addTextChangedListener {
            clearButton.visibility = if (it.toString().isNotEmpty()) View.VISIBLE else View.GONE
        }

        backButton.setOnClickListener {
            onBackPressed()
        }

        clearButton.setOnClickListener {
            search.text.clear()
            hideKeyboard()
        }

        search.setOnClickListener {
            search.requestFocus()
            showKeyboard()
        }

        weatherButton.setOnClickListener{
            val city = search.text.toString()
            val apiKey = "8c34f5691fcb4e6e9e1180730241704"
            val aqi = "no"
            apiService.getWeather(apiKey, city, aqi).enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    if (response.isSuccessful) {
                        val weatherResponse = response.body()
                        val temperatureC = weatherResponse?.current?.temp_c.toString()
                        search.setText(temperatureC)
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

    companion object {
        private const val SEARCH_TEXT_KEY = "search_text_key"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val searchText = search.text.toString()
        outState.putString(SEARCH_TEXT_KEY, searchText)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search.windowToken, 0)
    }

    private fun showKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT)
    }
}