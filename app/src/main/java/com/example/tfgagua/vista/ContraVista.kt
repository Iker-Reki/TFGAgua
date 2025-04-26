package com.example.tfgagua.vista

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tfgagua.ui.theme.TFGAguaTheme

@Composable
fun CambioContrasenaScreen(
    onPasswordChange: (String, String, String) -> Unit,
    onLogout: () -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Sección de cambio de contraseña
        Text(
            text = "CAMBIO DE CONTRASEÑA",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Formulario
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Contraseña actual")
            OutlinedTextField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()/*,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)*/
            )

            Text("Nueva contraseña")
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()/*,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)*/
            )

            Text("Confirmar contraseña")
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()/*,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)*/
            )

            Button(
                onClick = {
                    onPasswordChange(currentPassword, newPassword, confirmPassword)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Cambiar contraseña")
            }
        }

        // Espaciador
        Spacer(modifier = Modifier.height(32.dp))

        // Card de cerrar sesión
        Card(
            modifier = Modifier.fillMaxWidth(),


        ) {
            Button(
                onClick = { onLogout() },
                modifier = Modifier.fillMaxWidth(),

            ) {
                Text("CERRAR SESIÓN")
            }
        }
    }
}
@Preview
@Composable
fun PreviewCambioContrasena() {
    TFGAguaTheme {
        CambioContrasenaScreen(
            onPasswordChange = { current, new, confirm ->
                // Lógica para cambiar contraseña
            },
            onLogout = {
                // Lógica para cerrar sesión
            }
        )
    }
}