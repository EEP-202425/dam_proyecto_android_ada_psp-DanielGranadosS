package com.example.carpoolapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.carpoolapp.ui.theme.CarpoolAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CrearViajeScreen() {
    var fecha by remember { mutableStateOf(TextFieldValue("")) }
    var hora by remember { mutableStateOf(TextFieldValue("")) }
    var precio by remember { mutableStateOf(TextFieldValue("")) }
    var plazas by remember { mutableStateOf(TextFieldValue("")) }
    var mensaje by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        Text("Crear nuevo viaje", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha (YYYY-MM-DD)") })
        OutlinedTextField(value = hora, onValueChange = { hora = it }, label = { Text("Hora (HH:MM)") })
        OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio)") })
        OutlinedTextField(value = plazas, onValueChange = { plazas = it }, label = { Text("Plazas") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val nuevoViaje = Viaje(
                id = 0,
                fecha = fecha.text,
                hora = hora.text,
                precio = precio.text.toDoubleOrNull() ?: 0.0,
                plazas = plazas.text.toIntOrNull() ?: 0
            )

            RetrofitInstance.api.crearViaje(nuevoViaje).enqueue(object : Callback<Viaje> {
                override fun onResponse(call: Call<Viaje>, response: Response<Viaje>) {
                    mensaje = if (response.isSuccessful) "✅ Viaje creado" else "❌ Error al crear"
                }

                override fun onFailure(call: Call<Viaje>, t: Throwable) {
                    mensaje = "⚠️ Error: ${t.message}"
                }
            })
        }) {
            Text("Crear viaje")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(mensaje)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCrearViajeScreen() {
    CarpoolAppTheme {
        Surface {
            CrearViajeScreen()
        }
    }
}

