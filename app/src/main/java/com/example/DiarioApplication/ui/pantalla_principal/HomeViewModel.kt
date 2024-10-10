package com.example.DiarioApplication.ui.pantalla_principal


import androidx.lifecycle.viewModelScope
import com.example.DiarioApplication.data.Note.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.example.DiarioApplication.data.Note.NoteRepository
import kotlinx.coroutines.flow.StateFlow


class HomeViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    // Función para agregar una nueva nota
    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val newNote = Note(
                title = title,
                content = content,
                timestamp = System.currentTimeMillis()
            )
            noteRepository.insertNote(newNote)
        }
    }

    // Función para eliminar una nota
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNoteById(note.id)
        }
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


