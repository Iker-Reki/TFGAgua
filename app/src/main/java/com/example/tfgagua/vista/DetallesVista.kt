package com.example.tfgagua.vista

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource // Import colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tfgagua.R
import com.example.tfgagua.data.DatoAgua
import com.example.tfgagua.model.DetallesViewModel
import com.example.tfgagua.ui.theme.DarkBlue
import com.example.tfgagua.ui.theme.LightBlue
import java.text.ParseException // Import ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone // Import TimeZone for Z-suffix dates


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesVista(
    navController: NavHostController,
    confederacionId: Int,
    detallesViewModel: DetallesViewModel = viewModel()
) {
    val context = LocalContext.current
    val confederacionDetalle by detallesViewModel.confederacionDetalle.collectAsState()
    val datosAgua by detallesViewModel.datosAgua.collectAsState()
    val weatherData by detallesViewModel.weatherData.collectAsState()
    val isLoading by detallesViewModel.isLoading.collectAsState()
    val errorMensaje by detallesViewModel.errorMensaje.collectAsState()

    LaunchedEffect(confederacionId) {
        detallesViewModel.cargarDetallesConfederacion(confederacionId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(confederacionDetalle?.nombre ?: "Cargando...", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBlue)
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (errorMensaje != null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Error: $errorMensaje", color = Color.Red, style = MaterialTheme.typography.bodyLarge)
            }
            Log.e("DetallesVista", "Error al cargar: $errorMensaje")
        } else if (confederacionDetalle == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("No se pudieron cargar los detalles de la confederación.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    confederacionDetalle?.let { detalle ->
                        Text(
                            detalle.nombre,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = DarkBlue
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                DetailRow("Ubicación:", detalle.ubicacion)
                                DetailRow("Capacidad:", "${detalle.capacidad} m³")
                                DetailRow("Fecha de Construcción:", formatDateString(detalle.fecConstruccion))
                                DetailRow("Altura:", "${detalle.altura} metros")
                            }
                        }
                    }
                }

                item {
                    Text(
                        "Nivel del Agua Hoy",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    WaterLevelGraph(datosAgua = datosAgua)
                }

                item {
                    Text(
                        "Clima Actual",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    WeatherCard(weatherData = weatherData)
                }
            }
        }
    }
}

// Helper function to format the date string
fun formatDateString(dateString: String): String {
    // Input format example: 2020-01-15T00:00:00.000Z
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Important for 'Z' suffix

    // Output format example: 15/01/2020
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    return try {
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: "Fecha inválida"
    } catch (e: ParseException) {
        Log.e("DateFormatter", "Error parsing date: $dateString", e)
        "Fecha inválida"
    }
}


@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.SemiBold)
        Text(text = value)
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun WaterLevelGraph(datosAgua: List<DatoAgua>) {
    if (datosAgua.isEmpty()) {
        Text("No hay datos de nivel de agua para hoy.", modifier = Modifier.padding(vertical = 16.dp))
        return
    }

    val maxLevel = 300 // Assuming max level is 100% for the graph
    val barWidth = 40.dp
    val spacing = 16.dp

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp) // Height for the graph area
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            items(datosAgua.sortedBy { it.hora }) { dato ->
                val barHeight = (dato.nivel.toFloat() / maxLevel) * 150f // Max height 150dp
                val barColor = when {
                    dato.nivel < 70 -> Color.Green
                    dato.nivel < 200 -> colorResource(id = R.color.orange) // Corrected orange color
                    else -> Color.Red
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = "${dato.nivel}%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(barWidth)
                            .height(barHeight.dp)
                            .background(barColor, shape = MaterialTheme.shapes.small)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dato.hora.substring(0, 5), // Show HH:MM
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


@Composable
fun WeatherCard(weatherData: com.example.tfgagua.data.WeatherResponse?) {
    if (weatherData == null) {
        Text("No hay datos de clima disponibles.", modifier = Modifier.padding(vertical = 16.dp))
        return
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = weatherData.location.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = weatherData.current.condition.text, // This will now be in Spanish
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${weatherData.current.tempC}°C",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    text = "Humedad: ${weatherData.current.humidity}%",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Viento: ${weatherData.current.windKph} km/h",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            // Weather Icon
            val iconUrl = "https:${weatherData.current.condition.icon}"
            AsyncImage(
                model = iconUrl,
                contentDescription = weatherData.current.condition.text,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}