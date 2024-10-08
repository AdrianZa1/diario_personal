package com.example.DiarioApplication.data.Note

import android.content.Context
import androidx.room.Room

interface AppContainer {
    val noteRepository: NoteRepository
}

class NoteAppContainer(context: Context) : AppContainer {

    private val database: NoteDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_database"
        ).build()
    }

    override val noteRepository: NoteRepository by lazy {
        NoteRepository(database.noteDao())
    }
}