import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Estado para manejar el mensaje de error
    private val _loginError = MutableStateFlow<String?>(null)
    val loginError = _loginError.asStateFlow()

    // Validar las credenciales del usuario
    fun login(email: String, password: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByCredentials(email, password)
            if (user != null) {
                _loginError.value = null  // Credenciales correctas
                onLoginSuccess()  // Navegar al éxito de login
            } else {
                _loginError.value = "Usuario o contraseña incorrectos"  // Error en credenciales
            }
        }
    }
}
