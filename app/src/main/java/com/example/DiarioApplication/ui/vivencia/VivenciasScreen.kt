package com.example.DiarioApplication.ui.vivencia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.PushPin
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
import com.example.DiarioApplication.ui.pantalla_principal.NoteItem
import com.example.inventory.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VivenciasScreen(
    navController: NavHostController,
    vivenciasViewModel: VivenciasViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateToAddVivencia: () -> Unit
) {
    val vivencias by vivenciasViewModel.obtenerVivencias().collectAsState(initial = emptyList())

    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

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
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
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
                    IconButton(onClick = { /* Implementar acción del menú */ }) {
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
                            isSearching = !isSearching
                            if (!isSearching) {
                                searchText = ""
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddVivencia,
                containerColor = Color.Red
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Añadir nueva vivencia",
                    tint = Color.White
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
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
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(filteredVivencias) { vivencia ->
                            VivenciaItem(
                                vivencia = vivencia,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set("vivenciaId", vivencia.id)
                                    navController.navigate("vivenciaDetail")
                                },
                                onPinClick = { vivenciasViewModel.onPinClick(vivencia) },
                                onAddToFavoritesClick = { vivenciasViewModel.onAddToFavoritesClick(vivencia) }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun VivenciaItem(
    vivencia: Note,
    onClick: () -> Unit,
    onPinClick: () -> Unit,
    onAddToFavoritesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
            .padding(8.dp)
            .background(Color.White, shape = MaterialTheme.shapes.medium),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = vivencia.title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = vivencia.content)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            IconButton(onClick = onPinClick) {
                Icon(
                    imageVector = if (vivencia.isPinned) Icons.Default.PushPin else Icons.Outlined.PushPin,
                    contentDescription = if (vivencia.isPinned) "Desfijar Nota" else "Fijar Nota",
                    tint = if (vivencia.isPinned) Color.Yellow else Color.Gray
                )
            }
            IconButton(onClick = onAddToFavoritesClick) {
                Icon(
                    imageVector = if (vivencia.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (vivencia.isFavorite) "Quitar de Favoritos" else "Añadir a Favoritos",
                    tint = if (vivencia.isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}
