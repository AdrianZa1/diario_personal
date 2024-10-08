package com.example.DiarioApplication

import android.app.Application
import com.example.DiarioApplication.data.Note.AppContainer
import com.example.DiarioApplication.data.Note.NoteAppContainer

class NoteAppContainer : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = NoteAppContainer(this) // Inicializa el contenedor en la aplicación
    }
}
