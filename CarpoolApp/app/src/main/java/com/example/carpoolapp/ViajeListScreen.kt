package com.example.carpoolapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.carpoolapp.ui.theme.CarpoolAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ViajeListScreen(navController: NavController) {
    var viajes by remember { mutableStateOf<List<Viaje>>(emptyList()) }

    LaunchedEffect(Unit) {
        RetrofitInstance.api.getViajes().enqueue(object : Callback<List<Viaje>> {
            override fun onResponse(call: Call<List<Viaje>>, response: Response<List<Viaje>>) {
                if (response.isSuccessful) viajes = response.body() ?: emptyList()
            }

            override fun onFailure(call: Call<List<Viaje>>, t: Throwable) {}
        })
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { navController.navigate("crear") }) {
            Text("Crear viaje")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate("filtros") }) {
            Text("Filtrar viajes")
        }
        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(viajes) { viaje ->
                ViajeCard(
                    viaje = viaje,
                    onEdit = { navController.navigate("editar/${viaje.id}") },
                    onDelete = { id ->
                        RetrofitInstance.api.deleteViaje(id).enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.isSuccessful) {
                                    viajes = viajes.filterNot { it.id == id }
                                }
                            }
                            override fun onFailure(call: Call<Void>, t: Throwable) {}
                        })
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewViajeListScreen() {
    CarpoolAppTheme {
        ViajeListScreen(navController = rememberNavController())
    }
}


