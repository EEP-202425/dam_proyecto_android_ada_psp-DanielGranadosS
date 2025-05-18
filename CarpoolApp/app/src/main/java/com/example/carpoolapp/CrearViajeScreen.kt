package com.example.carpoolapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.carpoolapp.ui.theme.CarpoolAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CrearViajeScreen(navController: NavController) {
    var fecha by remember { mutableStateOf(TextFieldValue("")) }
    var hora by remember { mutableStateOf(TextFieldValue("")) }
    var precio by remember { mutableStateOf(TextFieldValue("")) }
    var plazas by remember { mutableStateOf(TextFieldValue("")) }
    var mensaje by remember { mutableStateOf("") }

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

            override fun onFailure(call: Call<List<Destino>>, t: Throwable) {
                mensaje = "Error cargando destinos"
            }
        })
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Crear nuevo viaje", style = MaterialTheme.typography.titleLarge)

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
            val viaje = Viaje(
                fecha = fecha.text,
                hora = hora.text,
                precio = precio.text.toDoubleOrNull() ?: 0.0,
                plazas = plazas.text.toIntOrNull() ?: 0,
                destino = selectedDestino.value // ✅ contiene ID
            )

            RetrofitInstance.api.crearViaje(viaje).enqueue(object : Callback<Viaje> {
                override fun onResponse(call: Call<Viaje>, response: Response<Viaje>) {
                    mensaje = if (response.isSuccessful) "✅ Viaje creado" else "❌ Error al crear"
                }

                override fun onFailure(call: Call<Viaje>, t: Throwable) {
                    mensaje = "Error: ${t.message}"
                }
            })
        }) {
            Text("Crear viaje")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }

        Spacer(Modifier.height(8.dp))
        Text(mensaje)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCrearViajeScreen() {
    CarpoolAppTheme {
        CrearViajeScreen(navController = rememberNavController())
    }
}
