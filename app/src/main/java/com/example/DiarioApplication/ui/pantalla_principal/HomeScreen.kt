package com.example.DiarioApplication.ui.pantalla_principal


import CameraViewModel
import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.DiarioApplication.data.Note.Note
import com.example.inventory.ui.AppViewModelProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(currentTime: String, currentDate: String) {
    TopAppBar(
        title = { Text(text = "Diario", color = Color(0xFF607D8B), fontWeight = FontWeight.Bold) },
        actions = {
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
fun MainBottomBar(
    onHomeClick: () -> Unit,
    onPinClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onImageClick: () -> Unit,
    onSaveNoteClick: () -> Unit
) {
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
            Icon(Icons.Filled.PushPin, contentDescription = "Ver Notas Fijadas", tint = Color(0xFF607D8B))
        }
        IconButton(onClick = onFavoriteClick) {
            Icon(Icons.Filled.Favorite, contentDescription = "Ver Notas Favoritas", tint = Color(0xFF607D8B))
        }
        IconButton(onClick = onImageClick) {
            Icon(Icons.Filled.ImageSearch, contentDescription = "Añadir Imagen", tint = Color(0xFF607D8B))
        }
        IconButton(onClick = onSaveNoteClick) {
            Icon(Icons.Filled.Check, contentDescription = "Guardar Nota", tint = Color(0xFF607D8B))
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
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
    }
}

@Composable
fun HomeScreen(
    onImageClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    cameraViewModel: CameraViewModel = viewModel()
) {
    val currentTime = remember { getCurrentTime() }
    val currentDate = remember { getCurrentDate() }
    val notes by viewModel.notes.collectAsState()

    var newNoteTitle by remember { mutableStateOf("") }
    var newNoteContent by remember { mutableStateOf("") }

    val imagePath by cameraViewModel.imagePath.collectAsState()

    Scaffold(
        topBar = {
            MainTopAppBar(
                currentTime = currentTime,
                currentDate = currentDate
            )
        },
        containerColor = Color(0xFFE0F7FA),
        bottomBar = {
            MainBottomBar(
                onHomeClick = onNavigateToHome,
                onPinClick = { /* Implementar lógica para ver notas fijadas */ },
                onFavoriteClick = { /* Implementar lógica para ver notas favoritas */ },
                onImageClick = onImageClick,
                onSaveNoteClick = {
                    if (newNoteTitle.isNotEmpty() && newNoteContent.isNotEmpty()) {
                        viewModel.addNote(newNoteTitle, newNoteContent)
                        newNoteTitle = ""
                        newNoteContent = ""
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo para el título de la nota
            TextField(
                value = newNoteTitle,
                onValueChange = { newNoteTitle = it },
                placeholder = { Text("Título de la Nota") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Campo para el contenido de la nota
            TextField(
                value = newNoteContent,
                onValueChange = { newNoteContent = it },
                placeholder = { Text("Contenido de la Nota") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Mostrar la imagen guardada
            imagePath?.let { path ->
                val bitmap = BitmapFactory.decodeFile(path)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Imagen seleccionada o capturada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 16.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            // Lista de notas
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