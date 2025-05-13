package com.example.carpoolapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carpoolapp.ui.theme.CarpoolAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarpoolAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var mostrarFormulario by remember { mutableStateOf(false) }

                    Column(modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)) {

                        Button(onClick = { mostrarFormulario = !mostrarFormulario }) {
                            Text(if (mostrarFormulario) "Ver viajes" else "Crear viaje")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (mostrarFormulario) {
                            CrearViajeScreen()
                        } else {
                            ViajeList()
                        }
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPantallaCombinada() {
    CarpoolAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            var mostrarFormulario by remember { mutableStateOf(false) }

            Column(modifier = Modifier.padding(16.dp)) {
                Button(onClick = { mostrarFormulario = !mostrarFormulario }) {
                    Text(if (mostrarFormulario) "Ver viajes" else "Crear viaje")
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (mostrarFormulario) {
                    CrearViajeScreen()
                } else {
                    ViajeList()
                }
            }
        }
    }
}
