package com.example.tfgagua.vista

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tfgagua.data.Confederacion
import com.example.tfgagua.model.ConfederacionViewModel
import com.example.tfgagua.ui.theme.DarkBlue // Asegúrate de tener estos colores definidos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaVista(
    navController: NavHostController,
    confederacionViewModel: ConfederacionViewModel = viewModel()
) {
    val context = LocalContext.current
    val confederacionesState by confederacionViewModel.confederaciones.collectAsState()
    val isLoading by confederacionViewModel.isLoading.collectAsState()
    val errorMensaje by confederacionViewModel.errorMensaje.collectAsState()

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Cargar las confederaciones cuando la vista se compone por primera vez
    LaunchedEffect(Unit) {
        confederacionViewModel.cargarConfederaciones()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WaterWarn", color = Color.White) },
                actions = {
                    IconButton(onClick = { navController.navigate("cerrarSesion") }) { // Asume que tienes una ruta "cerrarSesion" para CerrarSesVista
                        Icon(Icons.Filled.Settings, contentDescription = "Configuración", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBlue) // Usa tu color DarkBlue
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                "Todas las Confederaciones",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Buscador
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por nombre o ubicación") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (errorMensaje != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: $errorMensaje", color = Color.Red, style = MaterialTheme.typography.bodyLarge)
                }
                Log.e("ListaVista", "Error al cargar: $errorMensaje")
            } else {
                val filteredList = if (searchQuery.text.isBlank()) {
                    confederacionesState
                } else {
                    confederacionesState.filter {
                        it.nombre.contains(searchQuery.text, ignoreCase = true) ||
                                it.ubicacion.contains(searchQuery.text, ignoreCase = true)
                    }
                }

                if (filteredList.isEmpty() && searchQuery.text.isNotBlank()){
                    Text(
                        "No se encontraron confederaciones que coincidan con '${searchQuery.text}'.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                } else if (filteredList.isEmpty() && confederacionesState.isNotEmpty()){
                    Text(
                        "No hay confederaciones disponibles en este momento.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }


                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filteredList) { confederacion ->
                        ConfederacionCard(confederacion = confederacion) {
                            // TODO: Implementar navegación a DetallesVista.kt
                            // navController.navigate("detallesVista/${confederacion.id}")
                            Toast.makeText(context, "Ir a detalles de ${confederacion.nombre}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConfederacionCard(confederacion: Confederacion, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row( // Usamos Row para disposición horizontal
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) { // Columna para que el texto se expanda
                Text(
                    text = confederacion.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = confederacion.ubicacion,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 12.sp // Tamaño más pequeño para la ubicación
                )
            }
        }
    }
}