package com.example.clothing_store.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clothing_store.R

class ItemsActivity : AppCompatActivity() {

    private lateinit var toSearchButton: ImageButton
    private lateinit var basketButton: ImageButton
    private lateinit var favouritesButton: ImageButton
    private lateinit var accountButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toSearchButton = findViewById(R.id.toSearchButton)
        basketButton = findViewById(R.id.basketButton)
        favouritesButton = findViewById(R.id.favouritesButton)
        accountButton = findViewById(R.id.accountButton)

        toSearchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        basketButton.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        favouritesButton.setOnClickListener {
            val intent = Intent(this, FavouritesActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        accountButton.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}