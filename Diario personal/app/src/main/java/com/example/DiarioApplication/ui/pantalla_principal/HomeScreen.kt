package com.example.inventory.ui.pantalla_principal

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onHomeClick: () -> Unit,
    onPinClick: () -> Unit,
    onChatClick: () -> Unit,
    onAddToFavoritesClick: () -> Unit

) {
    val currentTime by remember { mutableStateOf(getCurrentTime()) }
    val currentDate by remember { mutableStateOf(getCurrentDate()) }

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
            Text(
                text = "Empieza a escribir...",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF90A4AE)
            )

            Spacer(modifier = Modifier.weight(1f))

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
    TopAppBar(
        title = { Text(text = "Título", color = Color(0xFF607D8B)) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF607D8B))
            }
        },
        actions = {
            IconButton(onClick = onAddToFavoritesClick) {
                Icon(Icons.Filled.Star, contentDescription = "Añadir a destacados", tint = Color(0xFF607D8B))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = currentDate, color = Color(0xFF607D8B))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = currentTime, color = Color(0xFF607D8B))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
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
            Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color(0xFF607D8B))
        }
        IconButton(onClick = onPinClick) {
            Icon(Icons.Filled.Favorite, contentDescription = "Pin", tint = Color(0xFF607D8B))
        }
        IconButton(onClick = onChatClick) {
            Icon(Icons.Filled.Email, contentDescription = "Chat", tint = Color(0xFF607D8B))
        }
    }
}

fun getCurrentTime(): String {
    val calendar = Calendar.getInstance()
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return timeFormat.format(calendar.time)
}

fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd, MMM yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(
        onHomeClick = { },
        onPinClick = { },
        onChatClick = { },
        onAddToFavoritesClick = { }
    )
}