package com.example.carpoolapp

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ViajeListScreen(navController: NavController) {
    var viajes by remember { mutableStateOf<List<Viaje>>(emptyList()) }

    LaunchedEffect(Unit) {
        RetrofitInstance.api.getViajes().enqueue(object : Callback<List<Viaje>> {
            override fun onResponse(call: Call<List<Viaje>>, response: Response<List<Viaje>>) {
                if (response.isSuccessful) {
                    viajes = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<Viaje>>, t: Throwable) {
                // Manejar error si quieres
            }
        })
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(viajes) { viaje ->
            ViajeCard(viaje) {
                navController.navigate("detalle/${viaje.id}")
            }
        }
    }
}


