package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.UserRepository
import com.tuapp.model.User

class UserEntryViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Mantiene el estado actual del usuario en la UI
    var userUiState by mutableStateOf(UserUiState())
        private set

    // Actualiza el estado del usuario y valida la entrada
    fun updateUiState(userDetails: UserDetails) {
        userUiState = UserUiState(userDetails = userDetails, isEntryValid = validateInput(userDetails))
    }


    suspend fun saveUser(newUser: User) {
        if (validateInput()) {
            userRepository.insertUser(userUiState.userDetails.toUser())
        }
    }

    // Valida la entrada del usuario
    private fun validateInput(uiState: UserDetails = userUiState.userDetails): Boolean {
        return with(uiState) {
            username.isNotBlank() && email.isNotBlank() && password.isNotBlank()
        }
    }
}

/**
 * Representa el estado de la UI para un usuario.
 */
data class UserUiState(
    val userDetails: UserDetails = UserDetails(),
    val isEntryValid: Boolean = false
)

data class UserDetails(
    val id: Int = 0,
    val username: String = "",
    val email: String = "",
    val password: String = "",
)

// Función de extensión para convertir UserDetails a User
fun UserDetails.toUser(): User = User(
    id = id,
    username = username,
    email = email,
    password = password
)