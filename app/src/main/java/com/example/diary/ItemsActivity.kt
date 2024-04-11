package com.example.diary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val basketButton: ImageButton = findViewById(R.id.basket_button)
        val favouritesButton: ImageButton = findViewById(R.id.favourites_button)
        val accountButton: ImageButton = findViewById(R.id.account_button)

        basketButton.setOnClickListener{
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }

        favouritesButton.setOnClickListener{
            val intent = Intent(this, FavouritesActivity::class.java)
            startActivity(intent)
        }

        accountButton.setOnClickListener{
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }
    }
}