package com.example.inventory.ui.pantalla_principal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import com.example.AplicacionesOfficce.ui.pantalla_principal.MainViewModel


// Pantalla principal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    Scaffold(
        topBar = { MainTopAppBar(onBackClick = { /* Handle back action */ }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textFieldValue by viewModel.textFieldValue.collectAsState()

            // Campo de texto
            TextField(
                value = textFieldValue,
                onValueChange = { viewModel.onTextFieldValueChange(it) },
                placeholder = { Text(text = "Empieza a escribir...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Puedes agregar más elementos aquí...

            Spacer(modifier = Modifier.weight(1f))

            // Barra inferior con acciones
            MainBottomBar(
                onHomeClick = { /* Handle home action */ },
                onPinClick = { /* Handle pin action */ },
                onChatClick = { /* Handle chat action */ }
            )
        }
    }
}

// Barra superior
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(onBackClick: () -> Unit) {
    SmallTopAppBar(
        title = { Text(text = "Home") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

// Barra inferior
@Composable
fun MainBottomBar(onHomeClick: () -> Unit, onPinClick: () -> Unit, onChatClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = onHomeClick) {
            Icon(Icons.Filled.Home, contentDescription = "Home")
        }
        IconButton(onClick = onPinClick) {
            Icon(Icons.Filled.Favorite, contentDescription = "Pin")
        }
        IconButton(onClick = onChatClick) {
            Icon(Icons.Filled.Email, contentDescription = "Chat")
        }
    }
}

// Vista previa de la pantalla principal
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}
