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

    // Eliminar una nota por ID
    suspend fun deleteNoteById(noteId: Int) {
        noteDao.deleteNoteById(noteId)
    }
    suspend fun updateNote(note: Note) {
        noteDao.update(note) // Asegúrate de tener este método en tu DAO
    }
    // Actualizar el estado de "Pin" de una nota
    suspend fun updatePinState(noteId: Int, isPinned: Boolean) {
        noteDao.updatePinState(noteId, isPinned)
    }

    // Actualizar el estado de "Favorito" de una nota
    suspend fun updateFavoriteState(noteId: Int, isFavorite: Boolean) {
        noteDao.updateFavoriteState(noteId, isFavorite)
    }
}


