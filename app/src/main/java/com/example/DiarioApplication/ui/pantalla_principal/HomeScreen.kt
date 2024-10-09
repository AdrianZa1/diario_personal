package com.example.DiarioApplication.ui.pantalla_principal


import CameraViewModel
import android.app.Activity
import android.provider.MediaStore
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.DiarioApplication.data.Note.Note
import com.example.inventory.ui.AppViewModelProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.graphics.BitmapFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    onAddToFavoritesClick: () -> Unit,
    currentTime: String,
    currentDate: String
) {
    TopAppBar(
        title = { Text(text = "Diario", color = Color(0xFF607D8B), fontWeight = FontWeight.Bold) },
        actions = {
            IconButton(onClick = onAddToFavoritesClick) {
                Icon(Icons.Filled.Star, contentDescription = "Añadir a favoritos", tint = Color(0xFF607D8B))
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
fun MainBottomBar(
    onHomeClick: () -> Unit,
    onPinClick: () -> Unit,
    onImageClick: () -> Unit
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
            Icon(Icons.Filled.Favorite, contentDescription = "Pin", tint = Color(0xFF607D8B))
        }
        IconButton(onClick = onImageClick) {
            Icon(Icons.Filled.Create, contentDescription = "A   ñadir Imagen", tint = Color(0xFF607D8B))
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



@Composable
fun HomeScreen(
    onImageClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    cameraViewModel: CameraViewModel = viewModel()  // Para manejar la imagen
) {
    val currentTime = remember { getCurrentTime() }
    val currentDate = remember { getCurrentDate() }

    var newNoteTitle by remember { mutableStateOf("") }
    var newNoteContent by remember { mutableStateOf("") }

    // Observar la ruta de la imagen guardada
    val imagePath by cameraViewModel.imagePath.collectAsState()

    Scaffold(
        topBar = {
            MainTopAppBar(
                onAddToFavoritesClick = { /* Implementar lógica para agregar a favoritos */ },
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
            Spacer(modifier = Modifier.height(8.dp))

            // Título de la nota
            TextField(
                value = newNoteTitle,
                onValueChange = { newNoteTitle = it },
                placeholder = { Text("Título de la Nota") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Contenido de la nota
            TextField(
                value = newNoteContent,
                onValueChange = { newNoteContent = it },
                placeholder = { Text("Contenido de la Nota") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para añadir imagen
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = {
                        if (newNoteTitle.isNotEmpty() && newNoteContent.isNotEmpty()) {
                            viewModel.addNote(newNoteTitle, newNoteContent)
                            newNoteTitle = ""
                            newNoteContent = ""
                        }
                    }
                ) {
                    Icon(Icons.Filled.Check, contentDescription = "Guardar Nota", tint = Color(0xFF607D8B))
                }

                IconButton(onClick = onImageClick) {
                    Icon(Icons.Filled.AccountBox, contentDescription = "Añadir Imagen", tint = Color(0xFF607D8B))
                }
            }

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

            // Barra inferior
            MainBottomBar(
                onHomeClick = onNavigateToHome,
                onPinClick = { /* Implementar lógica de pin si es necesario */ },
                onImageClick = { onImageClick() }
            )
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

