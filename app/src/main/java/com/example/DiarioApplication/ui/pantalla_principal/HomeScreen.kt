package com.example.DiarioApplication.ui.pantalla_principal


import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.example.DiarioApplication.data.Note.Note
import com.example.DiarioApplication.data.Note.NoteRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToHome: () -> Unit, // Para navegar a la pantalla de inicio
) {
    val currentTime = remember { getCurrentTime() }
    val currentDate = remember { getCurrentDate() }
    val notes by viewModel.notes.collectAsState()
    Scaffold(
        topBar = {
            MainTopAppBar(
                onBackClick = { /* Acción de volver */ },
                onAddToFavoritesClick = { /* Aquí puedes implementar la lógica para agregar una nota a favoritos */ },
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
            notes.forEach { note ->
                NoteItem(
                    note = note,
                    onPinClick = { viewModel.onPinClick(note) },
                    onAddToFavoritesClick = { viewModel.onAddToFavoritesClick(note) }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            MainBottomBar(
                onHomeClick = onNavigateToHome,
                onPinClick = { /* Implementar lógica de pin si es necesario */ },
                onImageClick = { viewModel.onImageClick(LocalContext.current as Activity) }
            )
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onPinClick: () -> Unit,
    onAddToFavoritesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(BorderStroke(1.dp, Color.Gray)),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = note.title, fontWeight = FontWeight.Bold)
        Text(text = note.content)
        Row {
            IconButton(onClick = onPinClick) {
                Icon(Icons.Filled.Favorite, contentDescription = "Pin Note")
            }
            IconButton(onClick = onAddToFavoritesClick) {
                Icon(Icons.Filled.Star, contentDescription = "Add to Favorites")
            }
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
        title = { Text(text = "Diario", color = Color(0xFF607D8B)) },
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
fun MainBottomBar(onHomeClick: () -> Unit, onPinClick: () -> Unit, onImageClick: @Composable () -> Unit) {
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
        /*IconButton(onClick = onImageClick) {
            Icon(Icons.Filled.Create, contentDescription = "Imagen", tint = Color(0xFF607D8B))
        }*/
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
