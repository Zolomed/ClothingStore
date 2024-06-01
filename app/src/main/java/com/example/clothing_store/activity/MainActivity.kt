package com.example.clothing_store.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clothing_store.App
import com.example.clothing_store.R
import com.example.clothing_store.model.login.LoginReq
import com.example.clothing_store.model.login.LoginResp
import com.example.clothing_store.retrofit.Retrofit
import com.google.android.material.switchmaterial.SwitchMaterial
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var userPhone: EditText
    private lateinit var userPass: EditText
    private lateinit var authButton: Button
    private lateinit var linkToReg: TextView
    private lateinit var errorText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userPhone = findViewById(R.id.userPhone)
        userPass = findViewById(R.id.userPass)
        authButton = findViewById(R.id.authButton)
        linkToReg = findViewById(R.id.linkToReg)
        errorText = findViewById(R.id.errorTextLog)

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val otherLayout = inflater.inflate(R.layout.activity_account, null)
        val themeSwitcher: SwitchMaterial = otherLayout.findViewById(R.id.themeSwitcher)

        themeSwitcher.isChecked = (applicationContext as App).darkTheme
        (applicationContext as App).switchTheme(themeSwitcher.isChecked)

        authButton.setOnClickListener {
            val phone = userPhone.text.toString().trim()
            val password = userPass.text.toString().trim()

            val user = LoginReq(phone, password)

            errorText.visibility = View.GONE

            Retrofit.myService.getLog(user).enqueue(object : Callback<LoginResp> {
                override fun onResponse(
                    call: Call<LoginResp>,
                    response: Response<LoginResp>
                ) {
                    if (response.code() == 200) {
                        val token = response.body()?.token
                        Toast.makeText(this@MainActivity, "Ваш токен: $token", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@MainActivity, ItemsActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        Log.e(
                            "WeatherApp",
                            "Ошибка ответа: ${response.code()} ${response.errorBody()}"
                        )

                        errorText.text = response.errorBody()?.string()
                        errorText.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    Log.e("ApiError", "Request failed: ${t.message}")
                }

            })
        }

        linkToReg.setOnClickListener {
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}