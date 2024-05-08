package com.example.diary

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.adapter.WeatherAdapter
import com.example.diary.retrofit.WeatherApiRepo


class SearchActivity : AppCompatActivity() {

    private lateinit var search: EditText
    private lateinit var clearButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var rvWeather: RecyclerView
    private lateinit var weatherButton: Button
    private lateinit var errorText: TextView

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
        progressBar = findViewById(R.id.progress_bar)
        rvWeather = findViewById(R.id.rv_weather)
        weatherButton = findViewById(R.id.weather_button)
        errorText = findViewById(R.id.error_text)

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

        weatherButton.setOnClickListener {
            getAPIData()
        }

        errorText.setOnClickListener {
            getAPIData()
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

    private fun getAPIData() {
        val text = search.text.toString()
        val weatherApiRepo = WeatherApiRepo()

        weatherApiRepo.getDataFromApi(text, rvWeather, search, errorText, progressBar){weather ->
            val layoutManager = LinearLayoutManager(this)
            val adapter = WeatherAdapter(listOf(weather))
            rvWeather.adapter = adapter
            rvWeather.setLayoutManager(layoutManager)
        }
    }
}