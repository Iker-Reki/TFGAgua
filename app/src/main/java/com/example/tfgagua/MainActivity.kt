package com.example.tfgagua

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.tfgagua.data.Usu
import com.example.tfgagua.model.ConfederacionViewModel
import com.example.tfgagua.model.UsuViewModel
import com.example.tfgagua.model.DetallesViewModel // Importar DetallesViewModel
import com.example.tfgagua.ui.theme.TFGAguaTheme
import com.example.tfgagua.vista.CerrarSesVista
import com.example.tfgagua.vista.ContraVista
import com.example.tfgagua.vista.InicioVista
import com.example.tfgagua.vista.ListaVista
import com.example.tfgagua.vista.RegistroScreen
import com.example.tfgagua.vista.DetallesVista // Importar DetallesVista


class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TFGAguaTheme {
                Surface {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    ElementosMenu(navController = navController)
}


@Composable
fun ElementosMenu(navController: NavHostController) {
    val usuViewModel: UsuViewModel = viewModel()
    val confederacionViewModel: ConfederacionViewModel = viewModel()
    val detallesViewModel: DetallesViewModel = viewModel() // ViewModel para detalles

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(paddingValues)
        ) {

            composable("inicio") {
                InicioVista(
                    navigateToFormReg = { navController.navigate("registro") },
                    navigateTolista = { navController.navigate("listaConfederaciones") },
                    viewModel = usuViewModel
                )
            }

            composable("registro") {
                RegistroScreen(
                    navigateToInicio = { navController.navigate("inicio"){
                        popUpTo("inicio") { inclusive = true }

                    } }

                )
            }

            composable("listaConfederaciones") {
                ListaVista(
                    viewModel = usuViewModel,
                    navController = navController,
                    confederacionViewModel = confederacionViewModel,
                    onConfederacionClick = { idConfe ->
                        navController.navigate("detallesVista/$idConfe")
                    }
                )
            }

            composable("cerrarSesion") {
                CerrarSesVista(
                    viewModel = usuViewModel,
                    navController = navController
                )
            }

            composable("cambioContrasena") {
                ContraVista(
                    viewModel = usuViewModel,
                    navigateTolista = {navController.navigate("listaConfederaciones") },
                    navigateToInicio = { navController.navigate("inicio") }
                )
            }


            composable(
                route = "detallesVista/{confederacionId}",
                arguments = listOf(navArgument("confederacionId") { type = NavType.IntType })
            ) { backStackEntry ->
                val confederacionId = backStackEntry.arguments?.getInt("confederacionId")
                if (confederacionId != null) {
                    DetallesVista(
                        navController = navController,
                        confederacionId = confederacionId,
                        detallesViewModel = detallesViewModel
                    )
                } else {

                    Toast.makeText(navController.context, "Confederación ID no encontrado", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            }
        }
    }
}