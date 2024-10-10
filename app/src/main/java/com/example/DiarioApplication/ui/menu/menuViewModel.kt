package com.example.menu
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// Definimos las rutas de navegación
sealed class MenuRoutes(val route: String) {
    object MisVivencias : MenuRoutes("mis_vivencias")
    object NuevaVivencia : MenuRoutes("nueva_vivencia")
    object Perfil : MenuRoutes("perfil")
}

// ViewModel para manejar las acciones del menú
class MenuViewModel : ViewModel() {

    private val _navigationEvent = MutableLiveData<MenuRoutes>()
    val navigationEvent: LiveData<MenuRoutes> = _navigationEvent

    fun onMisVivenciasClick() {
        _navigationEvent.value = MenuRoutes.MisVivencias
    }

    fun onNuevaVivenciaClick() {
        _navigationEvent.value = MenuRoutes.NuevaVivencia
    }

    fun onPerfilClick() {
        _navigationEvent.value = MenuRoutes.Perfil
    }

    fun onCerrarSesionClick() {
        // Aquí puedes manejar el evento de cerrar sesión, por ejemplo, limpiar datos o navegar al login
    }
}
