package com.example.carpoolapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EditarViajeScreen(navController: NavController, id: Long) {
    var viaje by remember { mutableStateOf<Viaje?>(null) }
    var mensaje by remember { mutableStateOf("") }

    var fecha by remember { mutableStateOf(TextFieldValue("")) }
    var hora by remember { mutableStateOf(TextFieldValue("")) }
    var precio by remember { mutableStateOf(TextFieldValue("")) }
    var plazas by remember { mutableStateOf(TextFieldValue("")) }

    val destinos = remember { mutableStateOf<List<Destino>>(emptyList()) }
    val selectedDestino = remember { mutableStateOf<Destino?>(null) }
    val expanded = remember { mutableStateOf(false) }

    // Cargar destinos disponibles
    LaunchedEffect(Unit) {
        RetrofitInstance.api.getDestinos().enqueue(object : Callback<List<Destino>> {
            override fun onResponse(call: Call<List<Destino>>, response: Response<List<Destino>>) {
                if (response.isSuccessful) {
                    destinos.value = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<Destino>>, t: Throwable) {
                mensaje = "Error cargando destinos"
            }
        })
    }

    // Cargar el viaje a editar
    LaunchedEffect(id) {
        RetrofitInstance.api.getViajeById(id).enqueue(object : Callback<Viaje> {
            override fun onResponse(call: Call<Viaje>, response: Response<Viaje>) {
                response.body()?.let {
                    viaje = it
                    fecha = TextFieldValue(it.fecha ?: "")
                    hora = TextFieldValue(it.hora ?: "")
                    precio = TextFieldValue(it.precio.toString())
                    plazas = TextFieldValue(it.plazas.toString())
                    selectedDestino.value = it.destino
                }
            }

            override fun onFailure(call: Call<Viaje>, t: Throwable) {
                mensaje = "Error al cargar viaje"
            }
        })
    }

    if (viaje == null) {
        Text("Cargando viaje...")
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Editar viaje", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha") })
        OutlinedTextField(value = hora, onValueChange = { hora = it }, label = { Text("Hora") })
        OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") })
        OutlinedTextField(value = plazas, onValueChange = { plazas = it }, label = { Text("Plazas") })

        Spacer(Modifier.height(8.dp))

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

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            val updatedViaje = viaje!!.copy(
                fecha = fecha.text,
                hora = hora.text,
                precio = precio.text.toDoubleOrNull() ?: 0.0,
                plazas = plazas.text.toIntOrNull() ?: 0,
                destino = selectedDestino.value
            )

            RetrofitInstance.api.updateViaje(id, updatedViaje).enqueue(object : Callback<Viaje> {
                override fun onResponse(call: Call<Viaje>, response: Response<Viaje>) {
                    if (response.isSuccessful) {
                        mensaje = "Viaje actualizado"
                        navController.popBackStack()
                    } else {
                        mensaje = "Error al actualizar"
                    }
                }

                override fun onFailure(call: Call<Viaje>, t: Throwable) {
                    mensaje = "Error: ${t.message}"
                }
            })
        }) {
            Text("Guardar cambios")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }

        Spacer(Modifier.height(8.dp))
        Text(mensaje)
    }
}

