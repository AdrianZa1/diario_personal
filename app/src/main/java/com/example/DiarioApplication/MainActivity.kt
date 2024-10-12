/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.DiarioApplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.DiarioApplication.ui.navigation.InventoryNavHost
import com.example.DiarioApplication.ui.theme.InventoryTheme
import com.example.inventory.R
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Variable para controlar si el tema oscuro está activado o no
            var isDarkThemeEnabled by remember { mutableStateOf(false) }

            // Función para alternar el tema
            val onThemeChange = {
                isDarkThemeEnabled = !isDarkThemeEnabled
            }

            InventoryTheme(darkTheme = isDarkThemeEnabled) { // Se utiliza el tema dinámicamente
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Mostrar el SplashScreen primero
                    SplashScreen {
                        // Luego del SplashScreen, navega a la siguiente pantalla
                        InventoryNavHost(
                            navController = navController,
                            isDarkThemeEnabled = isDarkThemeEnabled, // Pasamos el estado del tema
                            onThemeChange = onThemeChange // Función para cambiar el tema
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: @Composable () -> Unit) {
    var isTimeOut by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }

    // Simula un tiempo de espera de 3 segundos para el splash screen
    LaunchedEffect(Unit) {
        // Animación para incrementar el progreso en 3 segundos
        val duration = 3000 // 3 segundos
        val steps = 100 // Pasos para incrementar el progreso
        val interval = duration / steps.toLong() // Intervalo de tiempo entre cada incremento
        for (i in 1..steps) {
            delay(interval) // Espera entre cada incremento
            progress = i / steps.toFloat() // Calcula el progreso actual
        }
        isTimeOut = true // Después de 3 segundos, establece que el tiempo ha expirado
    }

    if (isTimeOut) {
        onTimeout() // Navega a la siguiente pantalla
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center, // Centra verticalmente todo el contenido
                modifier = Modifier.fillMaxSize()
            ) {
                // Ícono de la app
                Image(
                    painter = painterResource(id = R.mipmap.ic_logo_foreground),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Texto debajo del ícono
                Text(
                    text = "DIARIO PERSONAL\nTus pensamientos, siempre contigo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground, // Usa el color dependiendo del tema
                    modifier = Modifier.padding(top = 16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Progreso horizontal animado centrado
                LinearProgressIndicator(
                    progress = progress, // Asigna el valor de progreso actual
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // Ajusta el tamaño al 80% del ancho de la pantalla
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary, // Color del indicador de progreso
                    trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f) // Fondo del indicador
                )
            }
        }
    }
}
