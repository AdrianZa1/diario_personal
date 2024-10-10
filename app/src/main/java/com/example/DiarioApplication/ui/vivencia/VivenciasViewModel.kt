package com.example.DiarioApplication.ui.vivencia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DiarioApplication.data.Note.Note
import com.example.DiarioApplication.data.Note.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VivenciasViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    // Función para obtener las notas directamente del repositorio
    fun obtenerVivencias(): Flow<List<Note>> {
        return noteRepository.getAllNotes() // Asumiendo que tienes esta función en tu repositorio
    }

    // Función para agregar una nueva vivencia (opcional, si la necesitas)
    fun agregarVivencia(nuevaVivencia: String) {
        // Implementa la lógica para agregar una nueva vivencia en la base de datos si es necesario
    }
}
