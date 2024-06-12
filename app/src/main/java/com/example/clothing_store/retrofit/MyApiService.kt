package com.example.clothing_store.retrofit

import com.example.clothing_store.model.ItemList
import com.example.clothing_store.model.authorization.UserReq
import com.example.clothing_store.model.authorization.UserResp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApiService {

    @POST("register")
    fun getReg(@Body request: UserReq): Call<UserResp>

    @POST("login")
    fun getLog(@Body request: UserReq): Call<UserResp>

    @GET("items/fetch")
    fun getItems(): Call<ItemList>
}