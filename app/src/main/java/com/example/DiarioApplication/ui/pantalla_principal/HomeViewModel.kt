package com.example.DiarioApplication.ui.pantalla_principal


import androidx.lifecycle.viewModelScope
import com.example.DiarioApplication.data.Note.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.example.DiarioApplication.data.Note.NoteRepository
import kotlinx.coroutines.flow.StateFlow


class HomeViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val notes: StateFlow<List<Note>> = noteRepository.getAllNotes().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

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
}


