package com.example.diary

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.diary.retrofit.Api
import com.example.diary.retrofit.Weather
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.material.internal.ViewUtils.showKeyboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SearchActivity : AppCompatActivity() {

    companion object {
        private const val SEARCH_TEXT_KEY = "search_text_key"
    }

    private lateinit var search: EditText
    private lateinit var clearButton: Button
    private lateinit var backButton: Button
    private lateinit var weatherButton: Button
    private val baseUrl: String = "http://api.weatherapi.com/v1/"

    private val retrofit = Retrofit.Builder().baseUrl(baseUrl).build()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val searchText = search.text.toString()
        outState.putString(SEARCH_TEXT_KEY, searchText)
    }

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

        val api = retrofit.create(Api::class.java)

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
            val weather = api.getWeather(city_name = city).enqueue(object : Callback<Weather>{
                override fun onResponse(call: Call<Weather>, response:
                Response<Weather>
                ) {
                    // Получили ответ от сервера
                    if (response.isSuccessful) {
                        // Наш запрос был удачным, получаем наши объекты
                        val city = response.body().orEmpty()
                    } else {
                        // Сервер отклонил наш запрос с ошибкой
                        val errorJson = response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    // Не смогли присоединиться к серверу
                    // Выводим ошибку в лог, что-то пошло не так
                    t.printStackTrace()
                }
            })
            val editableValue = Editable.Factory.getInstance().newEditable(weather.current.temp_c.toString())
            search.text = editableValue
        }
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