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

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    // Función para obtener las notas directamente del repositorio
    fun obtenerVivencias(): Flow<List<Note>> {
        return noteRepository.getAllNotes() // Asumiendo que tienes esta función en tu repositorio
    }

    // Alternar el estado de fijar de la nota
    fun onPinClick(note: Note) {
        viewModelScope.launch {
            // Actualiza el estado de la nota fijada en la base de datos
            noteRepository.updatePinState(note.id, !note.isPinned)

            // Actualiza el estado en la lista local para reflejar el cambio
            val updatedNotes = _notes.value.map {
                if (it.id == note.id) {
                    it.copy(isPinned = !it.isPinned)
                } else {
                    it
                }
            }
            _notes.value = updatedNotes
        }
    }

    // Alternar el estado de favorito de la nota
    fun onAddToFavoritesClick(note: Note) {
        viewModelScope.launch {
            // Actualiza el estado de la nota favorita en la base de datos
            noteRepository.updateFavoriteState(note.id, !note.isFavorite)

            // Actualiza el estado en la lista local para reflejar el cambio
            val updatedNotes = _notes.value.map {
                if (it.id == note.id) {
                    it.copy(isFavorite = !it.isFavorite)
                } else {
                    it
                }
            }
            _notes.value = updatedNotes
        }
    }
}


