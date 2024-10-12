package com.example.DiarioApplication.ui.configuracion


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.DiarioApplication.data.Note.NoteRepository

class ConfiguracionViewModelFactory(private val noteRepository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfiguracionViewModel::class.java)) {
            return ConfiguracionViewModel(noteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
