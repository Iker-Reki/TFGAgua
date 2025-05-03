package com.example.tfgagua

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tfgagua.ui.theme.TFGAguaTheme
import com.example.tfgagua.vista.InicioVista
import com.example.tfgagua.vista.RegistroScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContent {

            navController = rememberNavController()

            TFGAguaTheme {
                Surface {
                    ElementosMenu(navController, auth)
                }
            }
        }
    }

    //Se llama despues del onCreate
    /**
     * Si el auth esta ya asociado va directamente a la vista de lista de embalses
     */
    override fun onStart() {
        super.onStart()
        val usuActual = auth.currentUser
        if (usuActual != null){
            //navController.navigate("lista")
            Log.i("Prueba", "Estoy logeado")
        }
    }
}
/*
@Composable
fun MainScreen() {
    //NavController  - NavHost -
    val navController = rememberNavController()
    ElementosMenu(navController)
}

 */
@Composable
fun ElementosMenu(navController: NavHostController, auth: FirebaseAuth) {
    Scaffold(
//        topBar = {
//            TopBar(navController = navController)
//        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = "inicio") {


            composable("inicio") {
                InicioVista(
                    auth,
                    navigateToFormReg = {navController.navigate("registro")},
                    navigateTolista = {navController.navigate("lista")}
                )
            }

            composable("registro") {

                RegistroScreen(auth)

            }

            composable("lista") {



            }

        }
    }
}
