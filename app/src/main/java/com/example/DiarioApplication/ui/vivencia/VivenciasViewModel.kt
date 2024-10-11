package com.example.DiarioApplication.ui.vivencia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DiarioApplication.data.Etiqueta.Etiqueta
import com.example.DiarioApplication.data.Etiqueta.EtiquetaRepository
import com.example.DiarioApplication.data.Note.Note
import com.example.DiarioApplication.data.Note.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class VivenciasViewModel(
    private val noteRepository: NoteRepository,
    private val etiquetaRepository: EtiquetaRepository
) : ViewModel() {

    // Función para obtener las notas directamente del repositorio
    fun obtenerVivencias(): Flow<List<Note>> {
        return noteRepository.getAllNotes() // Asumiendo que tienes esta función en tu repositorio
    }

    // Función para alternar el estado "Pin" de una nota
    fun onPinClick(note: Note) {
        val updatedNote = note.copy(isPinned = !note.isPinned)
        viewModelScope.launch {
            noteRepository.updateNote(updatedNote)
        }
    }

    // Función para alternar el estado "Favorito" de una nota
    fun onAddToFavoritesClick(note: Note) {
        val updatedNote = note.copy(isFavorite = !note.isFavorite)
        viewModelScope.launch {
            noteRepository.updateNote(updatedNote)

            // Si la nota se marca como favorita, se crea una etiqueta relacionada
            if (updatedNote.isFavorite) {
                val etiqueta = Etiqueta(
                    nombre = note.title,
                    descripcion = note.content
                )
                etiquetaRepository.insertarEtiqueta(etiqueta)
            }
        }
    }

    // Función para eliminar una nota
    fun eliminarNota(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNoteById(note.id)
        }
    }
}



