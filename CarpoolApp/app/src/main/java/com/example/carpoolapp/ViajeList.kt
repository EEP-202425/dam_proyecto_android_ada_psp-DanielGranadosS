package com.example.carpoolapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carpoolapp.ui.theme.CarpoolAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ViajeList() {
    var viajes by remember { mutableStateOf<List<Viaje>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        RetrofitInstance.api.getViajes().enqueue(object : Callback<List<Viaje>> {
            override fun onResponse(call: Call<List<Viaje>>, response: Response<List<Viaje>>) {
                if (response.isSuccessful) {
                    viajes = response.body() ?: emptyList()
                } else {
                    error = "Error ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<Viaje>>, t: Throwable) {
                error = "Error: ${t.message}"
            }
        })
    }

    if (error != null) {
        Text("Error: $error", color = MaterialTheme.colorScheme.error)
    } else {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            items(viajes) { viaje ->
                ViajeCard(
                    viaje = viaje,
                    onEdit = { viajeSeleccionado ->
                        println("Editar: ${viajeSeleccionado.id}")
                    },
                    onDelete = { id ->
                        println("Eliminar viaje con ID: $id")
                    }
                )

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewViajeList() {
    CarpoolAppTheme {
        Surface {
            ViajeList()
        }
    }
}


