package com.example.carpoolapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.carpoolapp.ui.theme.CarpoolAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ViajeFiltroScreen(navController: NavController) {
    var viajes by remember { mutableStateOf<List<Viaje>>(emptyList()) }
    var mensaje by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf(TextFieldValue("")) }
    var precio by remember { mutableStateOf(TextFieldValue("")) }
    var plazas by remember { mutableStateOf(TextFieldValue("")) }

    val destinos = remember { mutableStateOf<List<Destino>>(emptyList()) }
    val selectedDestino = remember { mutableStateOf<Destino?>(null) }
    val expanded = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        RetrofitInstance.api.getDestinos().enqueue(object : Callback<List<Destino>> {
            override fun onResponse(call: Call<List<Destino>>, response: Response<List<Destino>>) {
                if (response.isSuccessful) {
                    destinos.value = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<Destino>>, t: Throwable) {}
        })
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Filtrar viajes", style = MaterialTheme.typography.titleLarge)

        Text("Selecciona destino")
        Box {
            OutlinedTextField(
                value = selectedDestino.value?.ciudad ?: "",
                onValueChange = {},
                label = { Text("Destino") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded.value = !expanded.value }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "expandir")
                    }
                }
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                destinos.value.forEach { destino ->
                    DropdownMenuItem(
                        text = { Text(destino.ciudad ?: "") },
                        onClick = {
                            selectedDestino.value = destino
                            expanded.value = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(fecha, { fecha = it }, label = { Text("Fecha") })
        OutlinedTextField(precio, { precio = it }, label = { Text("Precio") })
        OutlinedTextField(plazas, { plazas = it }, label = { Text("Plazas") })

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            try {
                RetrofitInstance.api.getViajesConFiltros(
                    destino = selectedDestino.value?.ciudad,
                    fecha = if (fecha.text.isNotBlank()) fecha.text else null,
                    precio = precio.text.toDoubleOrNull(),
                    plazas = plazas.text.toIntOrNull()
                ).enqueue(object : Callback<List<Viaje>> {
                    override fun onResponse(call: Call<List<Viaje>>, response: Response<List<Viaje>>) {
                        if (response.isSuccessful) {
                            viajes = response.body() ?: emptyList()
                            mensaje = ""
                        } else {
                            mensaje = "Error ${response.code()}"
                        }
                    }

                    override fun onFailure(call: Call<List<Viaje>>, t: Throwable) {
                        mensaje = "Error: ${t.message}"
                    }
                })
            } catch (e: Exception) {
                mensaje = "Excepción: ${e.message}"
            }
        }) {
            Text("Buscar viajes")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }

        Text(mensaje)

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
fun PreviewViajeFiltroScreen() {
    CarpoolAppTheme {
        ViajeFiltroScreen(navController = rememberNavController())
    }
}
