package com.example.DiarioApplication.ui.configuracion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.DiarioApplication.data.Note.NoteRepository
import androidx.compose.runtime.LaunchedEffect as LaunchedEffect1

@Composable
fun ConfiguracionScreen(
    navController: NavHostController,
    noteRepository: NoteRepository // Acepta el repositorio aquí
) {
    var message by remember { mutableStateOf("") }
    var isDeleting by remember { mutableStateOf(false) } // Estado para controlar la eliminación

    // Componente LaunchedEffect para realizar la eliminación
    if (isDeleting) {
        LaunchedEffect1(Unit) {
            noteRepository.deleteAllNotes()
            message = "Todas las notas han sido eliminadas."
            isDeleting = false // Resetea el estado después de la eliminación
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botón para eliminar todas las notas
        Button(onClick = {
            isDeleting = true // Cambia el estado a verdadero al hacer clic
        }) {
            Text(text = "Eliminar todas las notas")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message)

        Spacer(modifier = Modifier.height(16.dp))
        // Botón para volver a la pantalla anterior
        Button(onClick = {
            navController.popBackStack() // Volver a la pantalla anterior
        }) {
            Text(text = "Volver")
        }
    }
}
