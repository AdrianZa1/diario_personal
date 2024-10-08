package com.example.DiarioApplication.data.Note

class OfflineNoteRepository(private val noteRepository: NoteDao) {
    // Aquí podrías implementar almacenamiento local en caché, o sincronización sin conexión.
    suspend fun insertNoteOffline(note: Note) {
        // Lógica de inserción sin conexión
        noteRepository.insertNote(note)
    }

    fun getAllNotesOffline() = noteRepository.getAllNotes()

    suspend fun deleteNoteOffline(noteId: Int) {
        noteRepository.deleteNoteById(noteId)
    }
}
