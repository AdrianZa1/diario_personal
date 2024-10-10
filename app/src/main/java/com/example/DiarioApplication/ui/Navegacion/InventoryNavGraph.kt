package com.example.DiarioApplication.ui.navigation


import CameraScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.DiarioApplication.ui.pantalla_principal.HomeScreen
import com.example.inventory.ui.inicio_sesion.LoginScreen
import com.example.inventory.ui.inicio_sesion.RegisterScreen

@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login", // Pantalla inicial
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
        composable("home") {
            HomeScreen(
                onNavigateToHome = { navController.navigate("home") },
                onImageClick = { navController.navigate("camera") } // Navegar a la cámara
            )
        }
        composable("camera") { // Nueva ruta para la cámara
            CameraScreen(
                onCustomAction = { navController.navigate("home") } // Vuelve a la pantalla anterior tras capturar imagen
            )
        }
    }
}
