package com.example.tfgagua.model

import androidx.lifecycle.ViewModel
import com.example.tfgagua.conexion.RetrofitClient
import com.example.tfgagua.data.CambioContrasena
import com.example.tfgagua.data.UsuarioResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsuViewModel : ViewModel() {
    // Función para establecer un usuario de prueba
    fun setUsuarioDePrueba() {
        _usuario.value = UsuarioResponse(
            id = 1,
            nombre = "Usuario",
            apellido1 = "Prueba",
            apellido2 = "Apellido2",
            correo = "prueba@example.com"
        )
    }
    private val _usuario = MutableStateFlow<UsuarioResponse?>(null)
    val usuario: StateFlow<UsuarioResponse?> = _usuario.asStateFlow()

    fun setUsuario(usuario: UsuarioResponse?) {
        _usuario.value = usuario
    }

    suspend fun cambiarContrasena(
        nuevaContrasena: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = RetrofitClient.instancia.cambiarContrasena(
                CambioContrasena(
                    idUsuario = _usuario.value?.id ?: 0,
                    nuevaContrasena = nuevaContrasena
                )
            )

            if (response.isSuccessful && response.body()?.success == true) {
                onSuccess()
            } else {
                onError(response.body()?.message ?: "Error al cambiar contraseña")
            }
        } catch (e: Exception) {
            onError("Error de conexión: ${e.message}")
        }
    }
}