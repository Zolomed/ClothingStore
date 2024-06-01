package com.example.clothing_store.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clothing_store.R
import com.example.clothing_store.model.login.LoginReq
import com.example.clothing_store.model.login.LoginResp
import com.example.clothing_store.model.register.RegisterReq
import com.example.clothing_store.model.register.RegisterResp
import com.example.clothing_store.retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegActivity : AppCompatActivity() {

    private lateinit var userPhoneReg: EditText
    private lateinit var userPassReg: EditText
    private lateinit var regButton: Button
    private lateinit var linkToLog: TextView
    private lateinit var errorTextReg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userPhoneReg = findViewById(R.id.userPhoneReg)
        userPassReg = findViewById(R.id.userPassReg)
        regButton = findViewById(R.id.regButton)
        linkToLog = findViewById(R.id.linkToLog)
        errorTextReg = findViewById(R.id.errorTextReg)

        regButton.setOnClickListener {
            val phone = userPhoneReg.text.toString().trim()
            val password = userPassReg.text.toString().trim()

            val user = RegisterReq(phone, password)

            errorTextReg.visibility = View.GONE

            Retrofit.myService.getReg(user).enqueue(object : Callback<RegisterResp> {
                override fun onResponse(
                    call: Call<RegisterResp>,
                    response: Response<RegisterResp>
                ) {
                    if (response.code() == 200) {
                        val token = response.body()?.token

                        Toast.makeText(this@RegActivity, "Ваш токен: $token", Toast.LENGTH_SHORT)
                            .show()

                        val intent = Intent(this@RegActivity, ItemsActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        Log.e(
                            "WeatherApp",
                            "Ошибка ответа: ${response.code()} ${response.errorBody()}"
                        )

                        errorTextReg.text = response.errorBody()?.string()
                        errorTextReg.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<RegisterResp>, t: Throwable) {
                    Log.e("ApiError", "Request failed: ${t.message}")
                }

            })
        }

        linkToLog.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()

        }
    }
}