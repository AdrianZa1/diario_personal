package com.example.menu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.UserRepository
import com.tuapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Definimos las rutas de navegación
sealed class MenuRoutes(val route: String) {
    object MisVivencias : MenuRoutes("mis_vivencias")
    object NuevaVivencia : MenuRoutes("nueva_vivencia")
    object Perfil : MenuRoutes("perfil")
}

// ViewModel para manejar las acciones del menú
class MenuViewModel(private val userRepository: UserRepository): ViewModel() {

    // LiveData para almacenar el usuario actual
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    // Función para cargar el usuario actual
    fun loadCurrentUser(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserById(userId).collect { user ->
                _currentUser.value = user
            }
        }
    }
}

