package com.example.carpoolapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ViajeApi {
    @GET("api/viajes")
    fun getViajes(): Call<List<Viaje>>

    @POST("api/viajes")
    fun crearViaje(@Body viaje: Viaje): Call<Viaje>
}
