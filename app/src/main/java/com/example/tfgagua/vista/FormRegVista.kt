package com.example.tfgagua.vista

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tfgagua.ui.theme.DarkBlue
import com.example.tfgagua.ui.theme.LightBlue

@Preview
@Composable
fun RegistroScreen() {
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
                text = "REGISTRO",
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            )

            //Hace el subrayado del titulo
            HorizontalDivider(
                modifier = Modifier.padding(top = 4.dp),
                thickness = 2.dp,
                color = Color.Green
            )
        }

        // Card del formulario de registro
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                CompactCampoFormulario(
                    label = "Nombre",
                    value = nombre,
                    onValueChange = { nombre = it }
                )

                CompactCampoFormulario(
                    label = "Primer apellido",
                    value = ape1,
                    onValueChange = { ape1 = it }
                )

                CompactCampoFormulario(
                    label = "Segundo apellido",
                    value = ape2,
                    onValueChange = { ape2 = it }
                )

                CompactCampoFormulario(
                    label = "Correo electrónico",
                    value = correo,
                    onValueChange = { correo = it }
                )

                CompactCampoFormulario(
                    label = "Confirmar correo",
                    value = confirmCorreo,
                    onValueChange = { confirmCorreo = it }
                )

                CompactCampoFormulario(
                    label = "Contraseña",
                    value = contra,
                    onValueChange = { contra = it },
                    visualTransformation = PasswordVisualTransformation()
                )

                CompactCampoFormulario(
                    label = "Confirmar contraseña",
                    value = confirmContra,
                    onValueChange = { confirmContra = it },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        }

        // Botones
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { /* Acción de confirmar */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White
                )
            ) {
                Text("Confirmar")
            }

            Button(
                onClick = { /* Acción de cancelar */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("Cancelar")
            }
        }


        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun CompactCampoFormulario(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.caption.copy(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            ),
            modifier = Modifier.padding(bottom = 2.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            singleLine = true,
            visualTransformation = visualTransformation
        )
    }
}