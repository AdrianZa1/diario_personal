package com.example.inventory.ui

import LoginViewModel
import UserProfileViewModel
import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.DiarioApplication.DiarioApplication
import com.example.DiarioApplication.ui.NoteViewModel
import com.example.DiarioApplication.ui.configuracion.ConfiguracionViewModel
import com.example.DiarioApplication.ui.pantalla_principal.HomeViewModel
import com.example.DiarioApplication.ui.vivencia.VivenciasViewModel
import com.example.inventory.ui.item.UserEntryViewModel
import com.example.menu.MenuViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Inicializador para UserEntryViewModel
        initializer {
            val appContainer = inventoryApplication().container
            UserEntryViewModel(appContainer.userRepository)
        }

        // Inicializador para LoginViewModel
        initializer {
            val appContainer = inventoryApplication().container
            LoginViewModel(
            userRepository = appContainer.userRepository,
            preferences = appContainer.sharedPreferences
            )
        }

        // Inicializador para UserProfileViewModel
        initializer {
            val appContainer = inventoryApplication().container
            UserProfileViewModel(
                userRepository = appContainer.userRepository,
                preferences = appContainer.sharedPreferences
            )
        }

        // Inicializador para HomeViewModel
        initializer {
            val appContainer = inventoryApplication().container
            HomeViewModel(appContainer.noteRepository)
        }

        // Inicializador para VivenciasViewModel
        initializer {
            val appContainer = inventoryApplication().container
            VivenciasViewModel(appContainer.noteRepository, appContainer.etiquetaRepository)
        }

        // Inicializador para NoteViewModel
        initializer {
            val appContainer = inventoryApplication().container
            NoteViewModel(inventoryApplication(), appContainer.noteRepository)
        }

        // Inicializador para MenuViewModel
        initializer {
            val appContainer = inventoryApplication().container
            MenuViewModel(userRepository = appContainer.userRepository,
                preferences = appContainer.sharedPreferences) // Usa el repository directamente
        }

        //Inicializador para ConfiguracionViewModel
        initializer {
            val appContainer = inventoryApplication().container
            ConfiguracionViewModel(appContainer.noteRepository)
        }
    }
}

/**
 * Función de extensión para consultar el objeto [Application] y devolver una instancia de
 * [DiarioApplication].
 */
fun CreationExtras.inventoryApplication(): DiarioApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DiarioApplication)

