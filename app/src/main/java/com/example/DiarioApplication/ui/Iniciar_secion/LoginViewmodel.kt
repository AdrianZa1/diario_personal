import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
    private val userRepository: UserRepository,
    private val preferences: SharedPreferences // Agregamos SharedPreferences
) : ViewModel() {

    // Estado para manejar el mensaje de error
    private val _loginError = MutableStateFlow<String?>(null)
    val loginError = _loginError.asStateFlow()

    // Validar las credenciales del usuario
    fun login(email: String, password: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByCredentials(email, password)
            if (user != null) {
                // Guardar ID, nombre y correo en SharedPreferences
                preferences.edit()
                    .putInt("user_id", user.id)
                    .putString("user_name", user.username)  // Guardar nombre del usuario
                    .putString("user_email", user.email)  // Guardar correo del usuario
                    .apply()

                _loginError.value = null
                onLoginSuccess()
            } else {
                _loginError.value = "Usuario o contraseña incorrectos"
            }
        }
    }


    // Método para cerrar sesión y borrar el ID del usuario
    fun logout() {
        preferences.edit().remove("user_id").apply()
    }
}
