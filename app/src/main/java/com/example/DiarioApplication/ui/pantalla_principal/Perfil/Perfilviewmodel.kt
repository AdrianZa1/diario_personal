import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.UserRepository
import com.tuapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val userRepository: UserRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile = _userProfile.asStateFlow()

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val errorMessage = MutableStateFlow<String?>(null)

    // Cargar el perfil del usuario que ha iniciado sesión
    fun loadUserProfile() {
        viewModelScope.launch {
            val userId = preferences.getInt("user_id", -1) // Recuperar el ID del usuario autenticado
            if (userId != -1) {
                userRepository.getUserById(userId).collect { user ->
                    _userProfile.value = user
                    email.value = user.email
                    password.value = user.password
                }
            } else {
                _userProfile.value = null // No hay usuario autenticado
            }
        }
    }

    // Funciones para manejar cambios de texto
    fun onEmailChanged(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        password.value = newPassword
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    // Función para actualizar el perfil del usuario
    fun updateUserProfile() {
        if (password.value == confirmPassword.value) {
            userProfile.value?.let { user ->
                viewModelScope.launch {
                    val updatedUser = user.copy(email = email.value, password = password.value)
                    userRepository.updateUser(updatedUser)
                    _userProfile.value = updatedUser // Actualizar el estado en la UI
                    errorMessage.value = null // Limpiar mensaje de error
                }
            }
        } else {
            errorMessage.value = "Las contraseñas no coinciden"
        }
    }
}
