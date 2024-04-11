package com.example.diary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val logoutButton: ImageButton = findViewById(R.id.logout_button)
        val itemsButton: ImageButton = findViewById(R.id.items_button)
        val basketButton: ImageButton = findViewById(R.id.basket_button)
        val favouritesButton: ImageButton = findViewById(R.id.favourites_button)

        logoutButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        itemsButton.setOnClickListener{
            val intent = Intent(this, ItemsActivity::class.java)
            startActivity(intent)
        }

        basketButton.setOnClickListener{
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }

        favouritesButton.setOnClickListener{
            val intent = Intent(this, FavouritesActivity::class.java)
            startActivity(intent)
        }
    }
}