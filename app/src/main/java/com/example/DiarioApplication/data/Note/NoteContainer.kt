package com.example.DiarioApplication.data.Note

import android.app.Application
import android.content.Context

interface AppContainer {
    val noteRepository: NoteRepository // Verifica que esta línea esté presente
}

class DefaultAppContainer(context: Context) : AppContainer {
    override val noteRepository: NoteRepository by lazy {
        NoteRepository(NoteDatabase.getDatabase(context).noteDao())
    }
}


class NoteAppContainer : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this) // Inicializa el contenedor aquí
    }
}

