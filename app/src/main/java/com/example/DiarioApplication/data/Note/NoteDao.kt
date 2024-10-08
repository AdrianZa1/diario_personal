package com.example.DiarioApplication.data.Note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // Insertar una nueva nota
    @Insert
    suspend fun insertNote(note: Note)

    // Obtener todas las notas como un Flow
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<Note>>

    // Eliminar una nota por ID
    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)
}