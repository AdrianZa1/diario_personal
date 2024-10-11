import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.UserRepository
import com.tuapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class UserProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Estado del perfil del usuario
    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile = _userProfile.asStateFlow()

    // Cargar el perfil del usuario que ha iniciado sesión
    fun loadUserProfile(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserById(userId).collect { user ->
                _userProfile.value = user
            }
        }
    }

    // Actualizar el correo y la contraseña del usuario
    fun updateEmailAndPassword(user: User, newEmail: String, newPassword: String) {
        viewModelScope.launch {
            val updatedUser = user.copy(email = newEmail, password = newPassword)
            userRepository.updateUser(updatedUser)
            _userProfile.value = updatedUser  // Actualizar el estado en la UI
        }
    }
}
