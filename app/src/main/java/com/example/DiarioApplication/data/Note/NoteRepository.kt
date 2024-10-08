package com.example.DiarioApplication.data.Note

import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing [Note] objects.
 */
class NoteRepository(private val noteDao: NoteDao) {

    // Insertar una nota
    suspend fun insertNote(nota: Note) {
        noteDao.insertNote(nota)
    }

    // Obtener todas las notas (no necesita ser suspendida)
    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    // Eliminar una nota por ID
    suspend fun deleteNoteById(notaId: Int) {
        noteDao.deleteNoteById(notaId)
    }
}
