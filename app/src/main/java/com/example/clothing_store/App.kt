package com.example.clothing_store

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    lateinit var sharedPref: SharedPreferences
    var darkTheme : Boolean = false

    override fun onCreate() {
        super.onCreate()

        sharedPref = getSharedPreferences("settings", MODE_PRIVATE)
        darkTheme = sharedPref.getBoolean("darkTheme", false)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        sharedPref.edit().putBoolean("darkTheme", darkTheme).apply()
    }
}