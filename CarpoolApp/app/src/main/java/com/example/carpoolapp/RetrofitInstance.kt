package com.example.carpoolapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ViajeApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiBaseUrlProvider.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViajeApi::class.java)
    }
}
