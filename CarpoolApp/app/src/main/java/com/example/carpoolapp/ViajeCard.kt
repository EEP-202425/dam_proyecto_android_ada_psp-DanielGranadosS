package com.example.carpoolapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carpoolapp.ui.theme.CarpoolAppTheme

@Composable
fun ViajeCard(
    viaje: Viaje,
    onEdit: (Viaje) -> Unit,
    onDelete: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("📅 ${viaje.fecha}")
            Text("🕒 ${viaje.hora}")
            Text("💸 ${viaje.precio} €")
            Text("🚗 Plazas: ${viaje.plazas}")
            Text("📍 Destino: ${viaje.destino?.ciudad ?: "No especificado"}")


            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(
                    onClick = { onEdit(viaje) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("✏️ Editar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onDelete(viaje.id ?: 0L) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("🗑️ Borrar", color = MaterialTheme.colorScheme.onError)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewViajeCard() {
    CarpoolAppTheme {
        ViajeCard(
            viaje = Viaje(
                id = 1,
                fecha = "2025-06-01",
                hora = "10:30",
                precio = 20.0,
                plazas = 3
            ),
            onEdit = {},
            onDelete = {}
        )
    }
}


