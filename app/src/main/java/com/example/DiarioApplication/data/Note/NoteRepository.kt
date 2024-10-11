package com.example.DiarioApplication.data.Note

import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing [Note] objects.
 */
class NoteRepository(private val noteDao: NoteDao) {
    // Insertar una nota
    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    // Obtener todas las notas
    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    // Obtener una nota por ID
    suspend fun getNoteById(noteId: Int): Note {
        return noteDao.getNoteById(noteId)
    }

    // Eliminar una nota por ID
    suspend fun deleteNoteById(noteId: Int) {
        noteDao.deleteNoteById(noteId)
    }

    // Actualizar el estado de una nota
    suspend fun updateNote(note: Note) {
        noteDao.update(note)
    }
}


