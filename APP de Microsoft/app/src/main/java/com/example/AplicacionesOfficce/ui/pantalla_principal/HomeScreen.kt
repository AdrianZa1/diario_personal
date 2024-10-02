package com.example.inventory.ui.pantalla_principal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat



@Composable
fun MainScreen(
    onHomeClick: () -> Unit,
    onPinClick: () -> Unit,
    onChatClick: () -> Unit,
    onAddToFavoritesClick: () -> Unit
) {
    val currentTime = remember { getCurrentTime() }
    val currentDate = remember { getCurrentDate() }

    Scaffold(
        topBar = {
            MainTopAppBar(
                onBackClick = { /* Acción de volver */ },
                onAddToFavoritesClick = onAddToFavoritesClick,
                currentTime = currentTime,
                currentDate = currentDate
            )
        },
        containerColor = Color(0xFFE0F7FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Aquí puedes agregar más contenido
            Text(
                text = "Empieza a escribir...",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF90A4AE) // Texto gris claro
            )

            Spacer(modifier = Modifier.weight(1f))

            // Barra inferior con acciones
            MainBottomBar(
                onHomeClick = onHomeClick,
                onPinClick = onPinClick,
                onChatClick = onChatClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    onBackClick: () -> Unit,
    onAddToFavoritesClick: () -> Unit,
    currentTime: String,
    currentDate: String
) {
    SmallTopAppBar(
        { Text(text = "Título", color = Color(0xFF607D8B)) }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF607D8B)
                )
            }
        },
        actions = {
            IconButton(onClick = onAddToFavoritesClick) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Añadir a destacados",
                    tint = Color(0xFF607D8B)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = currentDate, color = Color(0xFF607D8B)) // Fecha gris oscuro
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = currentTime, color = Color(0xFF607D8B)) // Hora gris oscuro
        },
    )
}

@Composable
fun MainBottomBar(onHomeClick: () -> Unit, onPinClick: () -> Unit, onChatClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = onHomeClick) {
            Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color(0xFF607D8B)) // Icono gris oscuro
        }
        IconButton(onClick = onPinClick) {
            Icon(Icons.Filled.Favorite, contentDescription = "Pin", tint = Color(0xFF607D8B)) // Icono gris oscuro
        }
        IconButton(onClick = onChatClick) {
            Icon(Icons.Filled.Email, contentDescription = "Chat", tint = Color(0xFF607D8B)) // Icono gris oscuro
        }
    }
}

// Función para obtener la hora actual
fun getCurrentTime(): String {
    val calendar = Calendar.getInstance()
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return timeFormat.format(calendar.time)
}

// Función para obtener la fecha actual
fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd, MMM yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(
        onHomeClick = { /* Acción de Home */ },
        onPinClick = { /* Acción de Pin */ },
        onChatClick = { /* Acción de Chat */ },
        onAddToFavoritesClick = { /* Acción de Añadir a favoritos */ }
    )
}
