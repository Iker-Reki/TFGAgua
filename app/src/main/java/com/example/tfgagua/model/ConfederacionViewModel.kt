package com.example.tfgagua.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfgagua.conexion.RetrofitClient
import com.example.tfgagua.data.Confederacion
import com.example.tfgagua.data.FavoritoRequest // Importar FavoritoRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ConfederacionViewModel : ViewModel() {

    private val _confederaciones = MutableStateFlow<List<Confederacion>>(emptyList())
    val confederaciones: StateFlow<List<Confederacion>> = _confederaciones.asStateFlow()

    private val _confederacionesFavoritas = MutableStateFlow<List<Confederacion>>(emptyList())
    val confederacionesFavoritas: StateFlow<List<Confederacion>> = _confederacionesFavoritas.asStateFlow()

    private val _errorMensaje = MutableStateFlow<String?>(null)
    val errorMensaje: StateFlow<String?> = _errorMensaje.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun cargarConfederaciones() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMensaje.value = null
            try {
                val response = RetrofitClient.instancia.obtenerConfederaciones()
                if (response.isSuccessful) {
                    _confederaciones.value = response.body() ?: emptyList()
                } else {
                    _errorMensaje.value = "Error al cargar confederaciones: ${response.code()}"
                }
            } catch (e: IOException) {
                _errorMensaje.value = "Error de red: ${e.message}"
            } catch (e: HttpException) {
                _errorMensaje.value = "Error HTTP: ${e.message}"
            } catch (e: Exception) {
                _errorMensaje.value = "Error desconocido: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cargarConfederacionesFavoritas(idUsu: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMensaje.value = null
            try {
                val response = RetrofitClient.instancia.obtenerConfederacionesFavoritas(idUsu)
                if (response.isSuccessful) {
                    _confederacionesFavoritas.value = response.body() ?: emptyList()
                } else {
                    _errorMensaje.value = "Error al cargar confederaciones favoritas: ${response.code()}"
                }
            } catch (e: IOException) {
                _errorMensaje.value = "Error de red: ${e.message}"
            } catch (e: HttpException) {
                _errorMensaje.value = "Error HTTP: ${e.message}"
            } catch (e: Exception) {
                _errorMensaje.value = "Error desconocido: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorito(idUsu: Int, confederacion: Confederacion) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMensaje.value = null
            try {
                val isCurrentlyFavorite = _confederacionesFavoritas.value.any { it.id == confederacion.id }
                val response = if (isCurrentlyFavorite) {
                    RetrofitClient.instancia.eliminarFavorito(idUsu, confederacion.id)
                } else {
                    RetrofitClient.instancia.añadirFavorito(FavoritoRequest(idUsu, confederacion.id))
                }

                if (response.isSuccessful) {
                    // Recargar ambas listas para reflejar el cambio
                    cargarConfederaciones()
                    cargarConfederacionesFavoritas(idUsu)
                } else {
                    _errorMensaje.value = "Error al actualizar favorito: ${response.code()}"
                }
            } catch (e: IOException) {
                _errorMensaje.value = "Error de red al actualizar favorito: ${e.message}"
            } catch (e: HttpException) {
                _errorMensaje.value = "Error HTTP al actualizar favorito: ${e.message}"
            } catch (e: Exception) {
                _errorMensaje.value = "Error desconocido al actualizar favorito: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun isConfederacionFavorita(confederacionId: Int): Boolean {
        return _confederacionesFavoritas.value.any { it.id == confederacionId }
    }
}