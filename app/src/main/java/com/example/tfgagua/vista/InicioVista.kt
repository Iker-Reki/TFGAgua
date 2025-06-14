package com.example.tfgagua.vista


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfgagua.R
import com.example.tfgagua.conexion.RetrofitClient
import com.example.tfgagua.data.LoginRequest
import com.example.tfgagua.ui.theme.DarkBlue
import com.example.tfgagua.ui.theme.LightBlue
import com.example.tfgagua.ui.theme.NoSelectField
import com.example.tfgagua.ui.theme.SelectField
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.tfgagua.model.UsuViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun InicioVista(
    navigateToFormReg: () -> Unit = {},
    navigateTolista: () -> Unit = {},
    viewModel: UsuViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var contra by remember { mutableStateOf("") }




    Column(
        modifier = Modifier
            .fillMaxSize()
            //Degradado
            .background(Brush.verticalGradient(listOf(DarkBlue, LightBlue))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.icapp), contentDescription = "")
        //TODO: Hacer el icono mas grande
        Spacer(modifier = Modifier.weight(1f))

        Text(
            "INICIO",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))

        //--------------------------------------------TEXTFIELD-----------------------------------------------


        Text("Email", color = White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = email,
            onValueChange = { email = it },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor =  NoSelectField,
                focusedContainerColor = SelectField
            )
        )

        Spacer(Modifier.height(20.dp))

        Text("Contraseña", color = White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = contra,
            onValueChange = { contra = it },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor =  NoSelectField,
                focusedContainerColor = SelectField
            ),
            visualTransformation = PasswordVisualTransformation(),)


        Spacer(modifier = Modifier.weight(1f))

        //--------------------------------------------BOTONES-----------------------------------------------

        //________________________Boton de logearse_____________________________
        Button(
            onClick = {
                if (email.isBlank() || contra.isBlank()) {
                    Toast.makeText(context, "Ingrese email y contraseña", Toast.LENGTH_SHORT).show()
                } else {
                    scope.launch(Dispatchers.IO) {
                        try {
                            val loginRequest = LoginRequest(
                                correo = email.trim(),
                                contrasena = contra.trim()
                            )
                            val response = RetrofitClient.instancia.login(loginRequest).execute()

                            withContext(Dispatchers.Main) {
                                when {
                                    !response.isSuccessful -> {
                                        Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show()
                                    }
                                    response.body()?.success != true -> {
                                        Toast.makeText(context, response.body()?.error ?: "Error desconocido", Toast.LENGTH_SHORT).show()
                                    }
                                    else -> {
                                        viewModel.setUsuario(response.body()!!.usuario!!)
                                        navigateTolista()
                                    }
                                }
                            }

                        } catch (e: Exception) {
                            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
                        }
                    }
                }


            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text(text = "INICIAR SESIÓN", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        //________________________Texto de he olvidado contraseña_____________________________

            Text(
                text = "He olvidado la contraseña",
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        Toast.makeText(
                            context,
                            "Por favor, contacte con el servicio técnico",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                fontWeight = FontWeight.Bold
            )


        Spacer(modifier = Modifier.height(8.dp))

        //________________________Boton de registrarse_____________________________
        Button(
            onClick = { navigateToFormReg() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text(text = "REGISTRARSE", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        //________________________Boton de continuar sin inicio de sesion_____________________________
        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "FUNCIÓN PROXIMAMENTE DISPONIBLE",
                    Toast.LENGTH_LONG
                ).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text(text = "CONTINUAR SIN INICIO", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


