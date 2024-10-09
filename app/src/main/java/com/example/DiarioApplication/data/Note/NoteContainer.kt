package com.example.DiarioApplication.data.Note

import UserContainer
import android.content.Context
import androidx.room.Room
import com.example.inventory.data.UserRepository

interface AppContainer {
    val noteRepository: NoteRepository
}

class NoteAppContainer(context: Context, override val itemsRepository: UserRepository) : AppContainer, UserContainer {

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