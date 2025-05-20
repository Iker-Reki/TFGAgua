package com.example.tfgagua

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tfgagua.conexion.ApiService
import com.example.tfgagua.conexion.RetrofitClient
import com.example.tfgagua.data.Usu
import com.example.tfgagua.model.UsuViewModel
import com.example.tfgagua.ui.theme.TFGAguaTheme
import com.example.tfgagua.vista.InicioVista
import com.example.tfgagua.vista.ListaScreen
import com.example.tfgagua.vista.RegistroScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //auth = Firebase.auth
        enableEdgeToEdge()
        setContent {

            navController = rememberNavController()

            TFGAguaTheme {
                Surface {
                    //Prueba()
                   // ElementosMenu(navController)
                    MainScreen()
                }
            }
        }
    }


/*
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
*/
    private fun Prueba() {

        RetrofitClient.instancia.obtenerUsu().enqueue(object : Callback<List<Usu>> {
            override fun onResponse(call: Call<List<Usu>>, response: Response<List<Usu>>) {
                if (response.isSuccessful) {
                    val lista = response.body()
                    lista?.forEach {
                        Log.d("USUARIO", "ID: ${it.id}, Nombre: ${it.nombre}, Apellido 1: ${it.apellido1}, Apellido 2: ${it.apellido2}, Correo:${it.correo}")
                    }
                    Toast.makeText(this@MainActivity, "Usuarios cargados: ${lista?.size}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error al obtener usuarios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Usu>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                Log.d("ERROR","${t.message}")
            }
        })
    }
}

@Composable
fun MainScreen() {
    //NavController  - NavHost -
    val navController = rememberNavController()
    ElementosMenu(navController)
}


@Composable
fun ElementosMenu(navController: NavHostController) {
    val usuViewModel : UsuViewModel = viewModel()

    Scaffold(
//        topBar = {
//            TopBar(navController = navController)
//        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = "inicio") {

            composable("inicio") {
                InicioVista(
                    navigateToFormReg = {navController.navigate("registro")},
                    navigateTolista = {navController.navigate("lista")} ,
                    viewModel = usuViewModel
                )
            }

            composable("registro") {

                RegistroScreen()

            }

            composable("lista") {
                ListaScreen(viewModel = usuViewModel)


            }

        }
    }

}
