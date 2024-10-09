package com.example.DiarioApplication.ui.pantalla_principal

import android.app.Activity


import android.content.Intent
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import com.example.DiarioApplication.data.Note.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.example.DiarioApplication.data.Note.NoteRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class HomeViewModel(private val repository: NoteRepository) : ViewModel() {

    // Cambia la definición de notes para que use el flujo de datos
    val notes: StateFlow<List<Note>> = repository.getAllNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Función para agregar una nueva nota
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

    // Función para eliminar una nota
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNoteById(note.id)
        }
    }

    // Función para manejar el clic en la pantalla de inicio
    fun onHomeClick() {
        // Aquí puedes navegar a la pantalla de inicio
        // Dependiendo de cómo manejes la navegación en tu aplicación
    }

    // Alternar el estado de fijar de la nota
    fun onPinClick(note: Note) {
        viewModelScope.launch {
            val updatedNote = note.copy(isPinned = !note.isPinned) // Alternar el estado de fijar
            repository.insertNote(updatedNote) // Guarda la nota actualizada
        }
    }

    // Alternar el estado de favorito de la nota
    fun onAddToFavoritesClick(note: Note) {
        viewModelScope.launch {
            val updatedNote = note.copy(isFavorite = !note.isFavorite) // Alternar el estado de favorito
            repository.insertNote(updatedNote) // Guarda la nota actualizada
        }
    }

    // Función para abrir la cámara o la galería
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

