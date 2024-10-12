package com.example.DiarioApplication.ui.configuracion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DiarioApplication.data.Note.NoteRepository
import kotlinx.coroutines.launch

class ConfiguracionViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    fun deleteAllNotes() {
        viewModelScope.launch {
            noteRepository.deleteAllNotes() // Asegúrate de que esta función exista en tu repositorio
        }
    }
}
