package com.example.tfgagua.vista

import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegistroScreen(auth: FirebaseAuth) {
  /*  onRegister: (UserData) -> Unit,
    onCancel: () -> Unit
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
                    onRegister(
                        UserData(
                            nombre,
                            ape1,
                            ape2,
                            correo,
                            contra
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Green)
                    .padding(10.dp),

            ) {
                Text("Confirmar")
            }

            Button(
                onClick = { onCancel() },
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

data class UserData(
    val nombre: String,
    val ap1: String,
    val ape2: String,
    val correo: String,
    val contra: String
)

// Vista previa
@Preview
@Composable
fun PreviewRegistroScreen() {
    TFGAguaTheme {
        RegistroScreen(
            onRegister = { /* Lógica de registro */ },
            onCancel = { /* Lógica de cancelación */ }
        )
    }*/
}