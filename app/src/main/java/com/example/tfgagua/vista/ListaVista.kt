package com.example.tfgagua.vista

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
    val confederacionesFavoritasState by confederacionViewModel.confederacionesFavoritas.collectAsState()
    val isLoading by confederacionViewModel.isLoading.collectAsState()
    val errorMensaje by confederacionViewModel.errorMensaje.collectAsState()

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // TODO: Obtener el idUsu del usuario logeado (ej. desde SharedPreferences o un ViewModel de sesión)
    // Placeholder para el idUsu. DEBES reemplazar esto con el ID real del usuario logeado.
    val idUsu = 1 // Ejemplo: Asume que el usuario con ID 1 está logeado.

    // Cargar las confederaciones cuando la vista se compone por primera vez
    LaunchedEffect(Unit) {
        confederacionViewModel.cargarConfederaciones()
        confederacionViewModel.cargarConfederacionesFavoritas(idUsu)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WaterWarn", color = Color.White) },
                actions = {
                    IconButton(onClick = { navController.navigate("cerrarSesion") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Configuración", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBlue)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            // Título de Confederaciones Favoritas
            Text(
                "Confederaciones Favoritas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            // Carusel de Confederaciones Favoritas
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (confederacionesFavoritasState.isEmpty()) {
                Text(
                    "No tienes confederaciones favoritas aún.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Altura fija para el carrusel
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(confederacionesFavoritasState) { confederacion ->
                        FavoriteConfederationCard(confederacion = confederacion) {
                            // TODO: Implementar navegación a DetallesVista.kt
                            // navController.navigate("detallesVista/${confederacion.id}")
                            Toast.makeText(context, "Ir a detalles de ${confederacion.nombre}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Título de Todas las Confederaciones
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
                        val isFavorite = confederacionViewModel.isConfederacionFavorita(confederacion.id)
                        ConfederacionCard(
                            confederacion = confederacion,
                            isFavorite = isFavorite,
                            onFavoriteClick = {
                                confederacionViewModel.toggleFavorito(idUsu, confederacion)
                            }
                        ) {
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
fun FavoriteConfederationCard(confederacion: Confederacion, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(180.dp) // Ancho fijo para las cards del carrusel
            .height(180.dp) // Alto fijo para las cards del carrusel
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = confederacion.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = DarkBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = confederacion.ubicacion,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ConfederacionCard(confederacion: Confederacion, isFavorite: Boolean, onFavoriteClick: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Para separar el texto del icono
        ) {
            Column(modifier = Modifier.weight(1f)) {
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
                    fontSize = 12.sp
                )
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}