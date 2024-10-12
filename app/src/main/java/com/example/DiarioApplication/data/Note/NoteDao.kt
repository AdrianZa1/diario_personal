package com.example.DiarioApplication.data.Note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    // Insertar una nota
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    // Obtener todas las notas
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<Note>>

    @Update
    suspend fun update(note: Note)

    // Obtener una nota por ID
    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): Note

    // Eliminar una nota por ID
    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    // Actualizar el estado de "Pin" de una nota
    @Query("UPDATE notes SET isPinned = :isPinned WHERE id = :noteId")
    suspend fun updatePinState(noteId: Int, isPinned: Boolean)

    // Actualizar el estado de "Favorito" de una nota
    @Query("UPDATE notes SET isFavorite = :isFavorite WHERE id = :noteId")
    suspend fun updateFavoriteState(noteId: Int, isFavorite: Boolean)
}
