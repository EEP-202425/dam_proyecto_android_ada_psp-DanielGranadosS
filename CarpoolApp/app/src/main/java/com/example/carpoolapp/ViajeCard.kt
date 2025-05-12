package com.example.carpoolapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ViajeCard(viaje: Viaje, onClick: (Viaje) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(viaje) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("📅 ${viaje.fecha}")
            Text("🕒 ${viaje.hora}")
            Text("💸 ${viaje.precio} €")
            Text("🚗 Plazas: ${viaje.plazas}")
        }
    }
}


