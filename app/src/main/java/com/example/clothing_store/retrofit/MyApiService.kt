package com.example.clothing_store.retrofit

import com.example.clothing_store.model.login.LoginReq
import com.example.clothing_store.model.login.LoginResp
import com.example.clothing_store.model.register.RegisterReq
import com.example.clothing_store.model.register.RegisterResp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApiService {

    @POST("register")
    fun getReg(@Body request: RegisterReq): Call<RegisterResp>

    @POST("login")
    fun getLog(@Body request: LoginReq): Call<LoginResp>

}