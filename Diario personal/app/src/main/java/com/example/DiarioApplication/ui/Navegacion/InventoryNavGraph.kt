package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.inicio_sesion.LoginScreen
import com.example.inventory.ui.inicio_sesion.RegisterScreen

import com.example.inventory.ui.pantalla_principal.MainScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = "login", // Pantalla inicial
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("main") }, // Navegar a MainScreen después de iniciar sesión
                onNavigateToRegister = { navController.navigate("register") } // Navegar a RegisterScreen
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterClick = {
                    // Después de registrarse, regresar a LoginScreen
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true } // Elimina la pantalla de registro de la pila
                    }
                },
                onNavigateToLogin = { navController.navigate("login") } // Navegar a LoginScreen
            )
        }


    }
}