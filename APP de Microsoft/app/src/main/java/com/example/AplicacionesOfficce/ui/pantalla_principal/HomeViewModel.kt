package com.example.AplicacionesOfficce.ui.pantalla_principal


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _textFieldValue = MutableStateFlow("")
    val textFieldValue: StateFlow<String> = _textFieldValue

    // Función para manejar cambios en el campo de texto
    fun onTextFieldValueChange(newValue: String) {
        _textFieldValue.value = newValue
    }


    fun onHomeAction() {
        // Lógica para manejar la acción de la barra de navegación de inicio
    }

    fun onPinAction() {
        // Lógica para manejar la acción de la barra de navegación de pin
    }

    fun onChatAction() {
        // Lógica para manejar la acción de la barra de navegación de chat
    }
}
