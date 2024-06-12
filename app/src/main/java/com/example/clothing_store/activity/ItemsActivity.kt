package com.example.clothing_store.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothing_store.R
import com.example.clothing_store.adapter.ItemAdapter
import com.example.clothing_store.model.ItemList
import com.example.clothing_store.retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemsActivity : AppCompatActivity() {

    private lateinit var toSearchButton: ImageButton
    private lateinit var itemRV: RecyclerView
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
        itemRV = findViewById(R.id.itemRV)

        getItems()

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

    private fun getItems() {
        Retrofit.myService.getItems().enqueue(object : Callback<ItemList> {
            override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        val items = response.body()!!.items
                        itemRV.layoutManager = LinearLayoutManager(this@ItemsActivity)
                        val adapter = ItemAdapter(items)
                        itemRV.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<ItemList>, t: Throwable) {
                Log.e("ApiError", "Request failed: ${t.message}")
            }
        })
    }
}