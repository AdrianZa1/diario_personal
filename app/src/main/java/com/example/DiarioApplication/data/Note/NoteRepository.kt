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
    fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes()
    }

    // Eliminar una nota por ID
    suspend fun deleteNoteById(noteId: Int) {
        noteDao.deleteNoteById(noteId)
    }
}

