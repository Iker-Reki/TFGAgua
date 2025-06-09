package com.example.tfgagua.vista

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tfgagua.Validaciones
import com.example.tfgagua.model.UsuViewModel
import com.example.tfgagua.ui.theme.DarkBlue
import com.example.tfgagua.ui.theme.LightBlue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ContraVista(
    viewModel: UsuViewModel = UsuViewModel(),
    navigateTolista: () -> Unit = {},
    navigateToInicio: () -> Unit = {}
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var actualContra by remember { mutableStateOf("") }
    var newContra by remember { mutableStateOf("") }
    var confirmContra by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Brush.verticalGradient(listOf(DarkBlue, LightBlue)))
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CAMBIO DE CONTRASEÑA",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            )

            viewModel.usuario.collectAsState().value?.let { usuario ->
                Text(
                    text = "Usuario: ${usuario.nombre} ${usuario.apellido1}",
                    color = Color.White
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = 4.dp),
                thickness = 2.dp,
                color = Color.Green
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CompactCampoFormulario(
                    label = "Contraseña actual",
                    value = actualContra,
                    onValueChange = { actualContra = it },
                    visualTransformation = PasswordVisualTransformation()
                )

                CompactCampoFormulario(
                    label = "Nueva contraseña",
                    value = newContra,
                    onValueChange = { newContra = it },
                    visualTransformation = PasswordVisualTransformation()
                )

                CompactCampoFormulario(
                    label = "Confirmar nueva contraseña",
                    value = confirmContra,
                    onValueChange = { confirmContra = it },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {

                    val (newActual, newNew, newConfirm) = cambiarContra(
                        actualContra,
                        newContra,
                        confirmContra,
                        context,
                        viewModel,
                        scope
                    )
                    actualContra = newActual
                    newContra = newNew
                    confirmContra = newConfirm
                    navigateTolista()

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White
                )
            ) {
                Text("Cambiar contraseña")
            }

            Button(
                onClick = {
                    viewModel.setUsuario(null)
                    navigateToInicio()
                    Toast.makeText(context, "SESIÓN CERRADA", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("Cerrar sesión")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}
fun cambiarContra(
    actualContra: String,
    newContra: String,
    confirmContra: String,
    context: Context,
    viewModel: UsuViewModel,
    scope: CoroutineScope
) : Triple<String, String, String> { // Devuelve un Triple con los nuevos valores
    val validacion = Validaciones()

    return when {
        actualContra.isEmpty() -> {
            Toast.makeText(context, "Ingrese la contraseña actual", Toast.LENGTH_SHORT).show()
            Triple(actualContra, newContra, confirmContra)
        }
        newContra.isEmpty() -> {
            Toast.makeText(context, "Ingrese la nueva contraseña", Toast.LENGTH_SHORT).show()
            Triple(actualContra, newContra, confirmContra)
        }
        confirmContra.isEmpty() -> {
            Toast.makeText(context, "Confirme la nueva contraseña", Toast.LENGTH_SHORT).show()
            Triple(actualContra, newContra, confirmContra)
        }
        !validacion.coinciden(newContra, confirmContra) -> {
            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            Triple(actualContra, newContra, confirmContra)
        }
        else -> {
            scope.launch {
                viewModel.cambiarContrasena(
                    nuevaContrasena = newContra,
                    onSuccess = {
                        Toast.makeText(context, "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()
                    },
                    onError = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                )
            }
            Triple("", "", "") // Devuelve valores vacíos para limpiar los campos
        }
    }
}
