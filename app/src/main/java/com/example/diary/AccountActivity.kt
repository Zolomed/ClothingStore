package com.example.diary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.switchmaterial.SwitchMaterial

class AccountActivity : AppCompatActivity() {

    private lateinit var logoutButton: ImageButton
    private lateinit var itemsButton: ImageButton
    private lateinit var basketButton: ImageButton
    private lateinit var favouritesButton: ImageButton
    private lateinit var themeSwitcher: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        logoutButton = findViewById(R.id.logout_button)
        itemsButton = findViewById(R.id.items_button)
        basketButton = findViewById(R.id.basket_button)
        favouritesButton = findViewById(R.id.favourites_button)
        themeSwitcher = findViewById(R.id.theme_switcher)

        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        themeSwitcher.isChecked = (applicationContext as App).darkTheme
        (applicationContext as App).switchTheme(themeSwitcher.isChecked)

        themeSwitcher.setOnCheckedChangeListener {switcher,  checked->
            (applicationContext as App).switchTheme(checked)
        }

        itemsButton.setOnClickListener {
            val intent = Intent(this, ItemsActivity::class.java)
            startActivity(intent)
            finishAffinity()
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
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, ItemsActivity::class.java)
        startActivity(intent)
        finish()
    }
}