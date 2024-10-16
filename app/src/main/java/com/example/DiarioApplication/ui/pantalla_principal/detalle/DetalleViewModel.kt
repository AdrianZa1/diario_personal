package com.example.DiarioApplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.DiarioApplication.data.Note.Note
import com.example.DiarioApplication.data.Note.NoteDao
import com.example.DiarioApplication.data.Note.NoteRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

class NoteViewModel(application: Application, private val noteRepository: NoteRepository) : AndroidViewModel(application) {

    // Flow para la última nota
    private val _latestNote = MutableStateFlow<Note?>(null)
    val latestNote: StateFlow<Note?> = _latestNote

    // Flow para la imagen más reciente
    private val _latestImageFile = MutableStateFlow<File?>(null)
    val latestImageFile: StateFlow<File?> = _latestImageFile

    init {
        // Cargar datos al iniciar
        loadLatestNote()
        loadLatestImage()
    }

    private fun loadLatestNote() {
        viewModelScope.launch {
            // Obtener todas las notas y seleccionar la más reciente
            val notes = noteRepository.getAllNotes().first()
            _latestNote.value = notes.maxByOrNull { it.timestamp }
        }
    }

    private fun loadLatestImage() {
        viewModelScope.launch {
            val dir = File(getApplication<Application>().filesDir, "com.example.inventory")
            val files = dir.listFiles { file -> file.isFile && file.extension == "png" }
            _latestImageFile.value = files?.maxByOrNull { it.lastModified() }
        }
    }

    // Función para editar una nota existente
    fun editarNota(noteId: Int, newTitle: String, newContent: String) {
        viewModelScope.launch {
            // Obtener la nota existente por ID
            val existingNote = noteRepository.getNoteById(noteId)

            // Crear una nueva instancia de la nota con los valores actualizados
            val updatedNote = existingNote.copy(
                title = newTitle,
                content = newContent
            )

            // Actualizar la nota en la base de datos
            noteRepository.updateNote(updatedNote)

            // Si la nota editada es la misma que la última nota cargada, actualizamos el StateFlow
            if (_latestNote.value?.id == noteId) {
                _latestNote.value = updatedNote
            }
        }
    }
}
