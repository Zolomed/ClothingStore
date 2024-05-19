package com.example.clothing_store

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
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
import com.example.clothing_store.adapter.SearchHistoryAdapter
import com.example.clothing_store.adapter.WeatherAdapter
import com.example.clothing_store.retrofit.WeatherApiRepo


class SearchActivity : AppCompatActivity() {
    companion object {
        private const val SEARCH_TEXT_KEY = "search_text_key"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private lateinit var search: EditText
    private lateinit var clearButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var weatherRecyclerView: RecyclerView
    private lateinit var weatherButton: Button
    private lateinit var errorText: TextView
    private lateinit var searchHistoryRecyclerView: RecyclerView
    private lateinit var clearHistoryButton: Button

    private val searchRunnable = Runnable { getAPIData() }
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var sharedPreferencesSearch: SharedPreferences

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
        clearButton = findViewById(R.id.clearButton)
        backButton = findViewById(R.id.backButton)
        progressBar = findViewById(R.id.progressBar)
        weatherRecyclerView = findViewById(R.id.weatherRecyclerView)
        weatherButton = findViewById(R.id.weatherButton)
        errorText = findViewById(R.id.errorText)
        searchHistoryRecyclerView = findViewById(R.id.searchHistoryRecyclerView)
        clearHistoryButton = findViewById(R.id.clearHistoryButton)
        searchHistoryRecyclerView.layoutManager = LinearLayoutManager(this)

        sharedPreferencesSearch = getSharedPreferences("SearchHistory", MODE_PRIVATE)

        if (savedInstanceState != null) {
            val searchText = savedInstanceState.getString(SEARCH_TEXT_KEY)
            search.setText(searchText)
        }

        backButton.setOnClickListener {
            onBackPressed()
        }

        clearButton.setOnClickListener {
            search.text.clear()
            hideKeyboard()
        }

        clearHistoryButton.setOnClickListener {
            clearSearchHistory()
            val searchHistory = getSearchHistory().toList()
            val adapter = SearchHistoryAdapter(searchHistory)
            searchHistoryRecyclerView.adapter = adapter
            searchHistoryRecyclerView.visibility = View.GONE
            clearHistoryButton.visibility = View.GONE
        }

        search.setOnClickListener {
            search.requestFocus()
            val searchHistory = getSearchHistory().toList()
            val adapter = SearchHistoryAdapter(searchHistory)
            searchHistoryRecyclerView.adapter = adapter
            if (searchHistory.isNotEmpty()) {
                searchHistoryRecyclerView.visibility = View.VISIBLE
                clearHistoryButton.visibility = View.VISIBLE
            }
            showKeyboard()
        }

        search.addTextChangedListener {
            clearButton.visibility = if (it.toString().isNotEmpty()) View.VISIBLE else View.GONE
        }

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchDebounce()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        weatherButton.setOnClickListener {
            getAPIData()
        }

        errorText.setOnClickListener {
            getAPIData()
        }
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
        if (text != "") {
            val weatherApiRepo = WeatherApiRepo()
            saveSearchQuery(search.text.toString())
            searchHistoryRecyclerView.visibility = View.GONE
            clearHistoryButton.visibility = View.GONE

            weatherApiRepo.getDataFromApi(
                text,
                weatherRecyclerView,
                errorText,
                progressBar
            ) { weather ->
                val layoutManager = LinearLayoutManager(this)
                val weatherAdapter = WeatherAdapter(listOf(weather))
                weatherRecyclerView.adapter = weatherAdapter
                weatherRecyclerView.setLayoutManager(layoutManager)
            }
        }
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun saveSearchQuery(query: String) {
        val history = getSearchHistory().toMutableSet()

        if (history.size >= 10) {
            history.remove(history.last())
        }

        history.add(query)

        sharedPreferencesSearch.edit().putStringSet("history", history).apply()
    }

    private fun getSearchHistory(): Set<String> {
        return sharedPreferencesSearch.getStringSet("history", setOf()) ?: setOf()
    }

    private fun clearSearchHistory() {
        sharedPreferencesSearch.edit().remove("history").apply()
    }
}