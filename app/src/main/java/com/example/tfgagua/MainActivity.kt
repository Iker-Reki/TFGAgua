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
import com.example.tfgagua.data.Usu
import com.example.tfgagua.model.ConfederacionViewModel // Importar ViewModel
import com.example.tfgagua.model.UsuViewModel
import com.example.tfgagua.ui.theme.TFGAguaTheme
import com.example.tfgagua.vista.CerrarSesVista // Importar Vista
import com.example.tfgagua.vista.InicioVista
import com.example.tfgagua.vista.ListaVista // Importar Vista
import com.example.tfgagua.vista.RegistroScreen


class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController // No es necesario si se maneja en MainScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // navController = rememberNavController() // Se puede inicializar en MainScreen directamente
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
    // Crear instancias de ViewModels aquí si necesitas pasarlas a múltiples composables
    // o usar viewModel() directamente en cada composable como ya haces.
    ElementosMenu(navController = navController)
}


@Composable
fun ElementosMenu(navController: NavHostController) {
    val usuViewModel: UsuViewModel = viewModel()
    val confederacionViewModel: ConfederacionViewModel = viewModel() // ViewModel para confederaciones

    Scaffold { paddingValues -> // Eliminado el topBar de aquí si cada pantalla lo maneja
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(paddingValues) // Aplicar paddingValues del Scaffold
        ) {

            composable("inicio") {
                InicioVista(
                    navigateToFormReg = { navController.navigate("registro") },
                    navigateTolista = { navController.navigate("listaConfederaciones") }, // Cambiar a nueva ruta
                    viewModel = usuViewModel
                )
            }

            composable("registro") {
                RegistroScreen(
                    // Si RegistroScreen necesita navegar de vuelta o a otro lado:
                    // onNavigateBack = { navController.popBackStack() },
                    // onNavigateToLogin = { navController.navigate("inicio") { popUpTo("inicio") { inclusive = true } } }
                )
            }

            // Nueva ruta para la lista de confederaciones
            composable("listaConfederaciones") {
                ListaVista(
                    navController = navController,
                    confederacionViewModel = confederacionViewModel
                )
            }

            // Ruta para la vista de cerrar sesión/configuración
            composable("cerrarSesion") {
                CerrarSesVista(
                    viewModel = usuViewModel,
                    navController = navController
                )
            }

            composable("cambio-contrasena") { // Esta ruta parece redundante con InicioVista si solo navega a inicio
                // Considera si esta pantalla es distinta o si la lógica puede ir en otro lado
                // Por ahora, la dejo como estaba en tu código original.
                InicioVista(
                    viewModel = usuViewModel,
                    navigateTolista = { navController.navigate("inicio") } //
                )
            }
            // composable("lista") { // Esta era la ruta original para ListaScreen, la cambiamos por listaConfederaciones
            // ListaScreen(viewModel = usuViewModel)
            // }


            // TODO: Definir la ruta para "DetallesVista" cuando la crees
            // composable("detallesVista/{confederacionId}") { backStackEntry ->
            // val confederacionId = backStackEntry.arguments?.getString("confederacionId")
            // DetallesVista(navController = navController, confederacionId = confederacionId)
            // }
        }
    }
}