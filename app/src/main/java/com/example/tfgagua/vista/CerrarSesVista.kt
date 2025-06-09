package com.example.tfgagua.vista

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfgagua.model.UsuViewModel
import com.example.tfgagua.ui.theme.DarkBlue // Asegúrate de tener estos colores
import com.example.tfgagua.ui.theme.LightBlue // Asegúrate de tener estos colores

@Composable
fun CerrarSesVista(viewModel: UsuViewModel, navController: NavHostController) {
    val usuario by viewModel.usuario.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (usuario != null) {
            Text(
                "Perfil del Usuario",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            listOf(
                "ID" to usuario!!.id.toString(),
                "Nombre" to usuario!!.nombre,
                "Apellidos" to "${usuario!!.apellido1} ${usuario!!.apellido2}", //
                "Correo" to usuario!!.correo //
            ).forEach { (label, value) ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = label,
                        color = DarkBlue,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = value)
                    Divider(color = LightBlue, thickness = 1.dp)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.setUsuario(null) // Limpia el usuario del ViewModel
                    navController.navigate("inicio") { // Navega a InicioVista
                        popUpTo(navController.graph.startDestinationId) { // Limpia el backstack hasta el inicio
                            inclusive = true
                        }
                        launchSingleTop = true // Evita múltiples instancias de inicio
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Cerrar Sesión", color = Color.White)
            }
        } else {
            Text("No hay datos de usuario. Serás redirigido.")
            // Opcionalmente, redirigir si no hay usuario
            LaunchedEffect(Unit) {
                navController.navigate("inicio") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}