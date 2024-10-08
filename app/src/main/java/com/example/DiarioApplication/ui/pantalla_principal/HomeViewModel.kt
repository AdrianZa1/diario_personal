package com.example.DiarioApplication.ui.pantalla_principal

import android.app.Activity


import android.content.Intent
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DiarioApplication.data.Note.Note
import com.example.DiarioApplication.data.Note.NoteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NoteRepository) : ViewModel() {

    val notes: StateFlow<List<Note>> = repository.getAllNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val newNote = Note(
                title = title,
                content = content,
                timestamp = System.currentTimeMillis()
            )
            repository.insertNote(newNote)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNoteById(note.id)
        }
    }

    fun onHomeClick() {
        // Aquí puedes navegar a la pantalla de inicio
        // Dependiendo de cómo manejes la navegación en tu aplicación
    }

    fun onPinClick(note: Note) {
        // Alternar el estado de fijar de la nota
        viewModelScope.launch {
            val updatedNote = note.copy(isPinned = !note.isPinned) // Alternar el estado de fijar
            repository.insertNote(updatedNote) // Guarda la nota actualizada
        }
    }

    fun onAddToFavoritesClick(note: Note) {
        // Alternar el estado de favorito de la nota
        viewModelScope.launch {
            val updatedNote = note.copy(isFavorite = !note.isFavorite) // Alternar el estado de favorito
            repository.insertNote(updatedNote) // Guarda la nota actualizada
        }
    }

    fun onImageClick(activity: Activity) {
        // Abre la cámara o la galería
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}

