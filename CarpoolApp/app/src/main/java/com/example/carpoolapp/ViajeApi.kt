package com.example.carpoolapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ViajeApi {
    @GET("api/viajes/{id}")
    fun getViajeById(@Path("id") id: Long): Call<Viaje>

    @GET("api/viajes")
    fun getViajes(): Call<List<Viaje>>

    @POST("api/viajes")
    fun crearViaje(@Body viaje: Viaje): Call<Viaje>

    @GET("api/viajes")
    fun getViajesConFiltros(
        @Query("destino") destino: String?,
        @Query("fecha") fecha: String?,
        @Query("precio") precio: Double?,
        @Query("plazas") plazas: Int?
    ): Call<List<Viaje>>


    @PUT("api/viajes/{id}")
    fun updateViaje(@Path("id") id: Long, @Body viaje: Viaje): Call<Viaje>

    @DELETE("api/viajes/{id}")
    fun deleteViaje(@Path("id") id: Long): Call<Void>

    @GET("api/destinos")
    fun getDestinos(): Call<List<Destino>>



}
