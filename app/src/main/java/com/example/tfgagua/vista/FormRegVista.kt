package com.example.tfgagua.vista

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Preview
@Composable
fun RegistroScreen(
) {
    var nombre by remember { mutableStateOf("") }
    var ape1 by remember { mutableStateOf("") }
    var ape2 by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var confirmCorreo by remember { mutableStateOf("") }
    var contra by remember { mutableStateOf("") }
    var confirmContra by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Título con subrayado verde
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "REGISTRO",
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.Center)
            )

            // Línea verde debajo del título
            HorizontalDivider(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(top = 8.dp),
                thickness = 3.dp,
                color = Color.Green
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card con el formulario
        Card(
            modifier = Modifier.fillMaxWidth(),

        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Campos del formulario
                CampoFormulario(
                    label = "Nombre",
                    value = nombre,
                    onValueChange = { nombre = it }
                )

                CampoFormulario(
                    label = "Primer apellido",
                    value = ape1,
                    onValueChange = { ape1 = it }
                )

                CampoFormulario(
                    label = "Segundo apellido",
                    value = ape2,
                    onValueChange = { ape2 = it }
                )

                CampoFormulario(
                    label = "Correo electrónico",
                    value = correo,
                    onValueChange = { correo = it },

                )

                CampoFormulario(
                    label = "Confirmar correo electrónico",
                    value = confirmCorreo,
                    onValueChange = { confirmCorreo = it }
                )

                CampoFormulario(
                    label = "Contraseña",
                    value = contra,
                    onValueChange = { contra = it },
                    visualTransformation = PasswordVisualTransformation()
                )

                CampoFormulario(
                    label = "Confirmar contraseña",
                    value = confirmContra,
                    onValueChange = { confirmContra = it },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botones
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {

                },
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Green)
                    .padding(10.dp),

            ) {
                Text("Confirmar")
            }

            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Red)


            ) {
                Text("Cancelar")
            }
        }
    }
}

@Composable
private fun CampoFormulario(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = visualTransformation
        )
    }
}
