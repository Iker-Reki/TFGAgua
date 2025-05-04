package com.example.tfgagua.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.tfgagua.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    navController: NavController,
    title: String,
    showBackArrow: Boolean = true
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.img_atras),
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate("userProfileRoute")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.img_ajuste),
                    contentDescription = stringResource(id = R.string.user_profile)
                )
            }
        }
    )
}
