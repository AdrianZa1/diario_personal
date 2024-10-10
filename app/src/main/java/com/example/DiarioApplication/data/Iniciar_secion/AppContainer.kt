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
    private val database = AppDatabase.getDatabase(context)

    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(database.userDao())
    }

    override val noteRepository: NoteRepository by lazy {
        NoteRepository(database.noteDao())
    }

    override val etiquetaRepository: EtiquetaRepository by lazy {
        EtiquetaRepository(database.etiquetaDao()) // Agregado
    }

    val offlineEtiquetaRepository: OfflineEtiquetaRepository by lazy {
        OfflineEtiquetaRepository(etiquetaRepository) // Agregado
    }
}


