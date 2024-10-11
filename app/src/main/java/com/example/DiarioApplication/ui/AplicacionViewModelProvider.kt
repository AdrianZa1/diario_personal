package com.example.inventory.ui

import LoginViewModel
import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.DiarioApplication.DiarioApplication
import com.example.DiarioApplication.ui.NoteViewModel
import com.example.DiarioApplication.ui.pantalla_principal.HomeViewModel
import com.example.DiarioApplication.ui.vivencia.VivenciasViewModel
import com.example.inventory.ui.item.UserEntryViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for UserEntryViewModel
        initializer {
            val appContainer = inventoryApplication().container
            UserEntryViewModel(appContainer.userRepository)
        }

        initializer {
            val appContainer = inventoryApplication().container
            LoginViewModel(appContainer.userRepository)
        }

        // Initializer for HomeViewModel
        initializer {
            val appContainer = inventoryApplication().container
            HomeViewModel(appContainer.noteRepository)
        }

        //Initializer for VivenciasViewModel
         initializer{
            val appContainer = inventoryApplication().container
            VivenciasViewModel(appContainer.noteRepository, appContainer.etiquetaRepository) // Pasamos ambos repositorios
        }

        //Initializer for NoteViewModel
        initializer {
            val appContainer = inventoryApplication().container
            NoteViewModel(inventoryApplication(), appContainer.noteRepository)
        }

        //Initializer for NoteViewModel
        initializer {
            val appContainer = inventoryApplication().container
            NoteViewModel(inventoryApplication(), appContainer.noteRepository)
        }
    }
}


/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): DiarioApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DiarioApplication)
