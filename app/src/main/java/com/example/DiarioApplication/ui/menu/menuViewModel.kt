package com.example.menu

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MenuRoutes(val route: String) {
    object MisVivencias : MenuRoutes("vivencias") // Ruta corregida
    object NuevaVivencia : MenuRoutes("nuevaVivencia")
    data class Perfil(val userId: Int) : MenuRoutes("userProfile/$userId")
}

class MenuViewModel(
    private val userRepository: UserRepository,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _userName = MutableStateFlow(preferences.getString("user_name", "") ?: "")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userEmail = MutableStateFlow(preferences.getString("user_email", "") ?: "")
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()

    fun onCerrarSesionClick() {
        viewModelScope.launch {
            preferences.edit().clear().apply()
        }
    }

    fun refreshUserInfo() {
        viewModelScope.launch {
            _userName.value = preferences.getString("user_name", "") ?: ""
            _userEmail.value = preferences.getString("user_email", "") ?: ""
        }
    }
}
