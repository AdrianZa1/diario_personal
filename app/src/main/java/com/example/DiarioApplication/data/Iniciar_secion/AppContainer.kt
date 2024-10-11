import android.content.Context
import com.example.DiarioApplication.data.Etiqueta.EtiquetaRepository
import com.example.DiarioApplication.data.Etiqueta.OfflineEtiquetaRepository
import com.example.DiarioApplication.data.Note.NoteRepository
import com.example.inventory.data.AppDatabase
import com.example.inventory.data.OfflineUserRepository
import com.example.inventory.data.UserRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val userRepository: UserRepository
    val noteRepository: NoteRepository
    val etiquetaRepository: EtiquetaRepository
}



/**
 * [UserContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class DefaultAppContainer(context: Context) : AppContainer {
    // Inicialización de la base de datos
    private val database = AppDatabase.getDatabase(context)

    // Implementación de userRepository
    override val userRepository: UserRepository by lazy {
        UserRepository(database.userDao()) // Suponiendo que AppDatabase tiene un método userDao()
    }

    // Implementación de noteRepository
    override val noteRepository: NoteRepository by lazy {
        NoteRepository(database.noteDao())
    }

    // Implementación de etiquetaRepository
    override val etiquetaRepository: EtiquetaRepository by lazy {
        EtiquetaRepository(database.etiquetaDao())
    }

    // Implementación adicional si lo necesitas
    val offlineEtiquetaRepository: OfflineEtiquetaRepository by lazy {
        OfflineEtiquetaRepository(etiquetaRepository)
    }
}







