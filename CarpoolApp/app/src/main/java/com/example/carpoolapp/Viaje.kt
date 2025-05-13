package com.example.carpoolapp

data class Viaje(
    val id: Long = 0,
    val fecha: String,
    val hora: String,
    val precio: Double,
    val plazas: Int
)
