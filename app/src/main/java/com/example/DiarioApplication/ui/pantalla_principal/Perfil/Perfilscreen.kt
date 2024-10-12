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
    viewModel: UserProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBackClick: () -> Unit,
    userId: Int
) {
    // Estados observables desde el ViewModel
    val userProfile by viewModel.userProfile.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Cargar el perfil del usuario al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.loadUserProfile() // Esto carga el perfil desde el ViewModel
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
            onValueChange = { viewModel.onEmailChanged(it) }, // Cambiar directamente en el ViewModel
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChanged(it) }, // Cambiar directamente en el ViewModel
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation() // Ocultar el texto de la contraseña
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para confirmar la nueva contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChanged(it) }, // Cambiar directamente en el ViewModel
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

        // Botón para actualizar los datos del usuario
        Button(
            onClick = {
                viewModel.updateUserProfile()
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
