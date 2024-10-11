package com.example.DiarioApplication.ui.vivencia

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DiarioApplication.data.Etiqueta.Etiqueta
import com.example.DiarioApplication.data.Etiqueta.EtiquetaRepository
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

class VivenciasViewModel(
    private val noteRepository: NoteRepository,
    private val etiquetaRepository: EtiquetaRepository
)
    : ViewModel()

{


    // Función para obtener las notas directamente del repositorio
    fun obtenerVivencias(): Flow<List<Note>> {
        return noteRepository.getAllNotes() // Asumiendo que tienes esta función en tu repositorio
    }

    fun onPinClick(note: Note) {
        // Cambiar el estado de la nota a fijada o no fijada
        val updatedNote = note.copy(isPinned = !note.isPinned)
        viewModelScope.launch {
            noteRepository.updateNote(updatedNote)
        }
    }

    fun onAddToFavoritesClick(note: Note) {

        val updatedNote = note.copy(isFavorite = !note.isFavorite)

        viewModelScope.launch {

            noteRepository.updateNote(updatedNote)


            if (updatedNote.isFavorite) {
                val etiqueta = Etiqueta(
                    nombre = note.title,
                    descripcion = note.content
                )
                etiquetaRepository.insertarEtiqueta(etiqueta)
            }
        }
    }

}


