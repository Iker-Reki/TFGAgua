package com.example.tfgagua.vista
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfgagua.model.UsuViewModel
import com.example.tfgagua.ui.theme.DarkBlue
import com.example.tfgagua.ui.theme.LightBlue

@Composable
fun ListaScreen(viewModel: UsuViewModel) {
    val usuario by viewModel.usuario.collectAsState() // Observa cambios

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (usuario != null) {
            // Título
            Text(
                "Perfil del Usuario",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Datos en formato lista
            listOf(
                "ID" to usuario!!.id.toString(),
                "Nombre" to usuario!!.nombre,
                "Apellidos" to "${usuario!!.apellido1} ${usuario!!.apellido2}",
                "Correo" to usuario!!.correo
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
        } else {
            Text("No hay datos de usuario")
        }
    }
}

/*
@Composable
fun ConfederacionesScreen(
    navController: NavController,
    viewModel: ConfederacionesViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sección 1: Carrusel de favoritos
        Text(
            text = "Confederaciones favoritas",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        FavoritosCarrusel(
            favoritos = state.favoritos,
            onConfederacionClick = { id ->
                navController.navigate("detalles/$id")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sección 2: Buscador y lista
        Text(
            text = "Confederaciones",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        SearchBar(
            searchText = state.searchText,
            onSearchTextChanged = { viewModel.onSearchTextChanged(it) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ConfederacionesList(
            confederaciones = state.filteredConfederaciones,
            onConfederacionClick = { id ->
                navController.navigate("detalles/$id")
            }
        )
    }
}
@Composable
fun FavoritosCarrusel(
    favoritos: List<Confederacion>,
    onConfederacionClick: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(favoritos) { confederacion ->
            FavoriteConfederacionItem(
                confederacion = confederacion,
                onClick = { onConfederacionClick(confederacion.id) }
            )
        }
    }
}

@Composable
fun FavoriteConfederacionItem(
    confederacion: Confederacion,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.size(120.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = confederacion.imagenUrl,
                contentDescription = confederacion.nombre,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = confederacion.nombre,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        placeholder = { Text("Buscar por nombre o provincia") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
        singleLine = true
    )
}

@Composable
fun ConfederacionesList(
    confederaciones: List<Confederacion>,
    onConfederacionClick: (String) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(confederaciones) { confederacion ->
            ConfederacionCard(
                confederacion = confederacion,
                onClick = { onConfederacionClick(confederacion.id) }
            )
        }
    }
}

@Composable
fun ConfederacionCard(
    confederacion: Confederacion,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = confederacion.imagenUrl,
                contentDescription = confederacion.nombre,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = confederacion.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = confederacion.provincia,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
*/