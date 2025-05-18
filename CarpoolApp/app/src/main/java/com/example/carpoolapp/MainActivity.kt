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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carpoolapp.ui.theme.CarpoolAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(Bundle: Bundle?) {
        super.onCreate(Bundle)
        setContent {
            CarpoolAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "viajes") {
                    composable("viajes") { ViajeListScreen(navController) }
                    composable("crear") { CrearViajeScreen(navController) }
                    composable("filtros") { ViajeFiltroScreen(navController) }
                    composable("editar/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) { backStack ->
                        val id = backStack.arguments?.getLong("id") ?: 0L
                        EditarViajeScreen(navController, id)
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPantallaCombinada() {
    val navController = rememberNavController()

    CarpoolAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            var mostrarFormulario by remember { mutableStateOf(false) }

            Column(modifier = Modifier.padding(16.dp)) {
                Button(onClick = { mostrarFormulario = !mostrarFormulario }) {
                    Text(if (mostrarFormulario) "Ver viajes" else "Crear viaje")
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (mostrarFormulario) {
                    CrearViajeScreen(navController)
                } else {
                    ViajeListScreen(navController)
                }
            }
        }
    }
}

