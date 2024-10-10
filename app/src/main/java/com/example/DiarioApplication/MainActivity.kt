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
import com.example.DiarioApplication.data.Note.NoteRepository
import com.example.DiarioApplication.ui.navigation.InventoryNavHost
import com.example.DiarioApplication.ui.theme.InventoryTheme
import com.example.inventory.R
import kotlinx.coroutines.delay
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InventoryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    // Llama a la función InventoryNavHost y le pasa el navController
                    InventoryNavHost(
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: @Composable () -> Unit) {
    var isTimeOut by remember { mutableStateOf(false) }

    // Simula un tiempo de espera de 3 segundos para el splash screen
    LaunchedEffect(Unit) {
        delay(3000) // 3 segundos de espera
        isTimeOut = true
    }

    if (isTimeOut) {
        onTimeout() // Navega a la siguiente pantalla
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Ícono de la app
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Asegúrate de tener este recurso
                    contentDescription = "App Icon",
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Texto debajo del ícono
                Text(
                    text = "DIARIO PERSONAL\nTus pensamientos, siempre contigo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}