import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.ui.AppViewModelProvider

@Composable
fun UserProfileScreen(
    userId: Int, // El ID del usuario que inició sesión
    viewModel: UserProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBackClick: () -> Unit // Callback para manejar el evento de volver atrás
) {
    // Estados para el correo y la contraseña
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Obtenemos el perfil del usuario desde el ViewModel
    val userProfile by viewModel.userProfile.collectAsState()

    // Cargar los datos del usuario cuando la pantalla se monta
    LaunchedEffect(userId) {
        viewModel.loadUserProfile(userId)
    }

    // Actualizar el estado de los campos cuando los datos del usuario cambian
    LaunchedEffect(userProfile) {
        userProfile?.let {
            email = it.email
            password = it.password // Cargar la contraseña existente
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Perfil del Usuario",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation() // Ocultar el texto de la contraseña
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para confirmar la nueva contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation() // Ocultar el texto de la contraseña
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensaje de error si las contraseñas no coinciden
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
                if (password == confirmPassword) {
                    userProfile?.let { user ->
                        // Actualizar correo y contraseña
                        viewModel.updateEmailAndPassword(user, email, password)
                        errorMessage = null // Limpiar mensaje de error si todo está bien
                    }
                } else {
                    errorMessage = "Las contraseñas no coinciden"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actualizar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver a la pantalla anterior
        Button(
            onClick = { onBackClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}

