package com.example.inventory.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.DiarioApplication.DiarioApplication
import com.example.DiarioApplication.ui.pantalla_principal.HomeViewModel

import com.example.inventory.ui.item.UserEntryViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for ItemEntryViewModel
        initializer {
            UserEntryViewModel(inventoryApplication().container.itemsRepository)
        }
        // Initializer for MainViewModel
        initializer {
            HomeViewModel(inventoryApplication().container.noteRepository)
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): DiarioApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DiarioApplication)
