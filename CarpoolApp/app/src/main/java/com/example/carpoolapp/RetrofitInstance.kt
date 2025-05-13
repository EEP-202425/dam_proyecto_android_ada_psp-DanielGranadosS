package com.example.carpoolapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ViajeApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // ← esto apunta a tu backend desde el emulador
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViajeApi::class.java)
    }
}
