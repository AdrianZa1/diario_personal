package com.example.DiarioApplication.ui.vivencia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.DiarioApplication.data.Note.Note
import com.example.inventory.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VivenciasScreen(
    navController: NavHostController,
    vivenciasViewModel: VivenciasViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateToAddVivencia: () -> Unit
) {
    val vivencias by vivenciasViewModel.obtenerVivencias().collectAsState(initial = emptyList())

    // Estado para controlar si se está mostrando el campo de búsqueda
    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    // Filtrar vivencias basado en el texto de búsqueda
    val filteredVivencias = if (searchText.isEmpty()) {
        vivencias
    } else {
        vivencias.filter { it.title.contains(searchText, ignoreCase = true) || it.content.contains(searchText, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            placeholder = { Text("Buscar vivencias...") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedTextColor = Color.Black,  // Cambiado a focusedTextColor
                                unfocusedTextColor = Color.Black, // Puedes ajustar el color del texto desenfocado si lo deseas
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Black
                            )
                        )

                    } else {
                        Text(
                            text = "Vivencias",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Implement menu action */ }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Alternar entre modo de búsqueda y modo normal
                            isSearching = !isSearching
                            if (!isSearching) {
                                searchText = "" // Resetear el texto de búsqueda cuando se cierra el modo de búsqueda
                            }
                        }
                    ) {
                        Icon(
                            if (isSearching) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = if (isSearching) "Cerrar búsqueda" else "Buscar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (filteredVivencias.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = if (searchText.isEmpty()) "No hay vivencias aún." else "No se encontraron vivencias.",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(filteredVivencias) { vivencia ->
                                VivenciaItem(vivencia) {
                                    navController.currentBackStackEntry?.savedStateHandle?.set("vivenciaId", vivencia.id)
                                    navController.navigate("vivenciaDetail")
                                }
                            }
                        }
                    }
                }

                // Botón flotante centrado en la parte inferior
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    FloatingActionButton(
                        onClick = onNavigateToAddVivencia,
                        containerColor = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Añadir nueva vivencia",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun VivenciaItem(vivencia: Note, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Event,
            contentDescription = "Ícono de fecha",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = vivencia.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = vivencia.content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
