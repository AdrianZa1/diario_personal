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

    // Flujo de notas desde el repositorio
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

    // Alternar el estado de fijar de la nota
    fun onPinClick(note: Note) {
        viewModelScope.launch {
            val updatedNote = note.copy(isPinned = !note.isPinned)
            repository.insertNote(updatedNote)
        }
    }

    // Alternar el estado de favorito de la nota
    fun onAddToFavoritesClick(note: Note) {
        viewModelScope.launch {
            val updatedNote = note.copy(isFavorite = !note.isFavorite)
            repository.insertNote(updatedNote)
        }
    }

    // Función para abrir la cámara o la galería para añadir una imagen
    fun onImageClick(activity: Activity) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}


