package com.example.DiarioApplication.ui.vivencia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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

    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isFilteringFavorites by remember { mutableStateOf(false) }  // Estado para filtrar por favoritos

    val filteredVivencias = vivencias.filter { vivencia ->
        val matchesSearch = searchText.isEmpty() || vivencia.title.contains(searchText, ignoreCase = true) || vivencia.content.contains(searchText, ignoreCase = true)
        val matchesFavorites = !isFilteringFavorites || vivencia.isFavorite
        matchesSearch && matchesFavorites
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
                    IconButton(onClick = {
                        navController.navigate("menuScreen")
                    }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // Botón de búsqueda
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

                    // Botón de favoritos
                    IconButton(
                        onClick = {
                            isFilteringFavorites = !isFilteringFavorites
                        }
                    ) {
                        Icon(
                            imageVector = if (isFilteringFavorites) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFilteringFavorites) "Mostrar todas las vivencias" else "Mostrar solo favoritos",
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
                                    navController.navigate("vivenciaDetalle")
                                },
                                onPinClick = { vivenciasViewModel.onPinClick(vivencia) },
                                onAddToFavoritesClick = { vivenciasViewModel.onAddToFavoritesClick(vivencia) },
                                onDeleteClick = { vivenciasViewModel.eliminarNota(vivencia) } // Callback para eliminar
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
    onAddToFavoritesClick: () -> Unit,
    onDeleteClick: () -> Unit // Nuevo parámetro para manejar la eliminación de la nota
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
            // Botón para eliminar la nota
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar Nota",
                    tint = Color.Red
                )
            }
        }
    }
}
