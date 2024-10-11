package com.example.DiarioApplication.ui.navigation


import CameraScreen
import NoteScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.DiarioApplication.ui.pantalla_principal.HomeScreen
import com.example.DiarioApplication.ui.vivencia.VivenciasScreen
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
                onLoginSuccess = { navController.navigate("vivencias") },
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
        composable("camera") {
            CameraScreen(
                onCustomAction = { navController.navigate("home") } // Volver a la pantalla anterior tras capturar imagen
            )
        }
        composable("home"){
            HomeScreen(
                onImageClick = { navController.navigate("camera") },
                onNavigateToHome = { navController.navigate("vivencias") }
            )
        }
        composable("vivencias") { // Nueva ruta para la pantalla de vivencias
            VivenciasScreen(
                navController = navController,
                onNavigateToAddVivencia = { navController.navigate("home") },
            )
        }
        composable("vivenciaDetalle") { // Nueva ruta para la cámara
            NoteScreen(
                onHomeClick = { navController.navigate("vivencias") } // Vuelve a la pantalla anterior tras capturar imagen
           )
        }
    }
}

