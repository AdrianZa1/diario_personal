package com.example.inventory.ui.inicio_sesion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.item.UserEntryViewModel
import com.tuapp.model.User
import kotlinx.coroutines.launch


@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit, // Acción después del registro exitoso
    onNavigateToLogin: () -> Unit, // Navegación a pantalla de login
    viewModel: UserEntryViewModel  = viewModel(factory = AppViewModelProvider.Factory)
) {
     // Instanciando el ViewModel
    val coroutineScope = rememberCoroutineScope() // Para lanzar corrutinas
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "DIARIO PERSONAL",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tus pensamientos, siempre contigo",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        // Campo de Nombre
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombres y apellidos") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Campo de Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Campo de Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation() // Ocultando el texto de la contraseña
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Campo de Confirmación de Contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation() // Ocultando el texto de la contraseña
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensajes de error si existen
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }


        Button(
            onClick = {
                coroutineScope.launch {

                    if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                        errorMessage = "Por favor, complete todos los campos"
                        return@launch
                    }

                    if (password != confirmPassword) {
                        errorMessage = "Las contraseñas no coinciden"
                        return@launch
                    }

                    try {
                        val newUser = User(username = name, email = email, password = password)
                        viewModel.saveUser(newUser)

                        // Si la inserción es exitosa
                        errorMessage = null
                        onRegisterClick()
                    } catch (e: Exception) {
                        // Capturar y mostrar el error en caso de que ocurra algún problema
                        errorMessage = "Error al registrar el usuario: ${e.message}"
                        e.printStackTrace() // Registrar la excepción en el logcat
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
        

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de navegación a la pantalla de inicio de sesión
        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}