package com.example.DiarioApplication.ui.vivencia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    onNavigateToAddVivencia: () -> Unit,
    onNavigateToSearch: () -> Unit
) {
    val vivencias by vivenciasViewModel.obtenerVivencias().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Vivencias", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { /* Implement menu action */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Implement notification action */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = Color.White)
                    }
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddVivencia,
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir nueva vivencia")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.surfaceVariant) // Usa el color del tema
            ) {
                if (vivencias.isEmpty()) {
                    Text("No hay vivencias aún", modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    LazyColumn {
                        items(vivencias) { vivencia ->
                            VivenciaItem(vivencia) {
                                // Pass data using savedStateHandle
                                navController.currentBackStackEntry?.savedStateHandle?.set("vivenciaId", vivencia.id)
                                navController.navigate("vivenciaDetail")
                            }
                        }
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
            .clickable(onClick = onClick) // Mantenemos la indicación
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
