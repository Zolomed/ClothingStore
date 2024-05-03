package com.example.diary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BasketActivity : AppCompatActivity() {

    private lateinit var itemsButton: ImageButton
    private lateinit var favouritesButton: ImageButton
    private lateinit var accountButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_basket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        itemsButton = findViewById(R.id.items_button)
        favouritesButton = findViewById(R.id.favourites_button)
        accountButton = findViewById(R.id.account_button)

        itemsButton.setOnClickListener{
            val intent = Intent(this, ItemsActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        favouritesButton.setOnClickListener{
            val intent = Intent(this, FavouritesActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        accountButton.setOnClickListener{
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, ItemsActivity::class.java)
        startActivity(intent)
        finish()
    }
}