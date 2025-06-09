package com.example.tfgagua.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfgagua.conexion.RetrofitClient // Ensure this import is correct
import com.example.tfgagua.data.ConfederacionDetalle
import com.example.tfgagua.data.DatoAgua
import com.example.tfgagua.data.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.util.Log // Import Log for debugging

class DetallesViewModel : ViewModel() {

    private val _confederacionDetalle = MutableStateFlow<ConfederacionDetalle?>(null)
    val confederacionDetalle: StateFlow<ConfederacionDetalle?> = _confederacionDetalle.asStateFlow()

    private val _datosAgua = MutableStateFlow<List<DatoAgua>>(emptyList())
    val datosAgua: StateFlow<List<DatoAgua>> = _datosAgua.asStateFlow()

    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMensaje = MutableStateFlow<String?>(null)
    val errorMensaje: StateFlow<String?> = _errorMensaje.asStateFlow()

    private val WEATHER_API_KEY = "f485400fbbd04092ab774326250206"

    fun cargarDetallesConfederacion(idConfe: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMensaje.value = null
            try {

                val response = RetrofitClient.instancia.obtenerDetalleConfederacion(idConfe)
                if (response.isSuccessful) {
                    _confederacionDetalle.value = response.body()
                    _confederacionDetalle.value?.let { detail ->
                        cargarDatosAgua(detail.id)
                        cargarClima(detail.ubicacion)
                    }
                } else {
                    _errorMensaje.value = "Error al cargar detalles de la confederación: ${response.code()}"
                    Log.e("DetallesViewModel", "Error cargando confederacion: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: IOException) {
                _errorMensaje.value = "Error de red: ${e.message}"
                Log.e("DetallesViewModel", "Error de red al cargar confederacion: ${e.message}")
            } catch (e: HttpException) {
                _errorMensaje.value = "Error HTTP: ${e.message}"
                Log.e("DetallesViewModel", "Error HTTP al cargar confederacion: ${e.message}")
            } catch (e: Exception) {
                _errorMensaje.value = "Error desconocido: ${e.message}"
                Log.e("DetallesViewModel", "Error desconocido al cargar confederacion: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun cargarDatosAgua(confeId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMensaje.value = null
            try {
                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                val response = RetrofitClient.instancia.obtenerDatosAgua(confeId, currentDate)
                if (response.isSuccessful) {
                    _datosAgua.value = response.body() ?: emptyList()
                } else {
                    _errorMensaje.value = "Error al cargar datos de agua: ${response.code()}"
                    Log.e("DetallesViewModel", "Error cargando datos de agua: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: IOException) {
                _errorMensaje.value = "Error de red al cargar datos de agua: ${e.message}"
            } catch (e: HttpException) {
                _errorMensaje.value = "Error HTTP al cargar datos de agua: ${e.message}"
            } catch (e: Exception) {
                _errorMensaje.value = "Error desconocido al cargar datos de agua: ${e.message}"
            } finally {

            }
        }
    }

    private fun cargarClima(ubicacion: String) {
        viewModelScope.launch {
            _errorMensaje.value = null
            try {

                val response = RetrofitClient.weatherApiService.getCurrentWeather(WEATHER_API_KEY, ubicacion)
                if (response.isSuccessful) {
                    _weatherData.value = response.body()
                    Log.d("DetallesViewModel", "Weather data loaded: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMensaje.value = "Error al cargar el clima: ${response.code()} - $errorBody"
                    Log.e("DetallesViewModel", "Error loading weather: ${response.code()} - $errorBody")
                }
            } catch (e: IOException) {
                _errorMensaje.value = "Error de red al cargar el clima: ${e.message}"
                Log.e("DetallesViewModel", "Network error loading weather: ${e.message}")
            } catch (e: HttpException) {
                _errorMensaje.value = "Error HTTP al cargar el clima: ${e.message}"
                Log.e("DetallesViewModel", "HTTP error loading weather: ${e.message}")
            } catch (e: Exception) {
                _errorMensaje.value = "Error desconocido al cargar el clima: ${e.message}"
                Log.e("DetallesViewModel", "Unknown error loading weather: ${e.message}")
            }
        }
    }
}