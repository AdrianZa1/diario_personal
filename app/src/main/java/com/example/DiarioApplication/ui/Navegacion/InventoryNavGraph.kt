package com.example.DiarioApplication.ui.navigation

import CameraScreen
import LoginScreen
import NoteScreen
import UserProfileScreen // Importamos la pantalla del perfil de usuario
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.DiarioApplication.DiarioApplication
import com.example.DiarioApplication.ui.configuracion.ConfiguracionScreen
import com.example.DiarioApplication.ui.pantalla_principal.HomeScreen
import com.example.DiarioApplication.ui.vivencia.VivenciasScreen
import com.example.inventory.ui.inicio_sesion.RegisterScreen
import com.example.menu.MenuDesplegableScreen

@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isDarkThemeEnabled: Boolean,
    onThemeChange: () -> Unit
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
                onCustomAction = { navController.navigate("home") }
            )
        }
        composable("home") {
            HomeScreen(
                onImageClick = { navController.navigate("camera") },
                onNavigateToHome = { navController.navigate("vivencias") }
            )
        }
        composable("vivencias") {
            VivenciasScreen(
                navController = navController,
                onNavigateToAddVivencia = { navController.navigate("home") },
            )
        }
        composable("vivenciaDetalle") {
            NoteScreen(
                onHomeClick = { navController.navigate("vivencias") }
            )
        }
        composable("menuScreen") {
            MenuDesplegableScreen(navController = navController)
        }
        composable("configuracion") {
            ConfiguracionScreen(
                navController = navController,
                noteRepository = (LocalContext.current.applicationContext as DiarioApplication).container.noteRepository,
                isDarkThemeEnabled = isDarkThemeEnabled,
                onThemeChange = onThemeChange
            )
        }
        composable("nuevaVivencia") {
            HomeScreen(
                onImageClick = { navController.navigate("camera") },
                onNavigateToHome = { navController.navigate("vivencias") }
            )
        }
        composable("userProfile/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            UserProfileScreen(
                userId = userId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}