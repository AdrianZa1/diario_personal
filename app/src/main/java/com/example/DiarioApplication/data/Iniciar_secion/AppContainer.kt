import android.content.Context
import android.content.SharedPreferences
import com.example.DiarioApplication.data.Etiqueta.EtiquetaRepository
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
    val sharedPreferences: SharedPreferences // Nueva propiedad para las preferencias
}

/**
 * [DefaultAppContainer] provides instances of repositories and SharedPreferences.
 */
class DefaultAppContainer(context: Context) : AppContainer {

    // Inicialización de la base de datos
    private val database = AppDatabase.getDatabase(context)

    // Inicialización de SharedPreferences
    override val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Implementación de userRepository
    override val userRepository: UserRepository by lazy {
        UserRepository(database.userDao())
    }

    // Implementación de noteRepository
    override val noteRepository: NoteRepository by lazy {
        NoteRepository(database.noteDao())
    }

    // Implementación de etiquetaRepository
    override val etiquetaRepository: EtiquetaRepository by lazy {
        EtiquetaRepository(database.etiquetaDao())
    }
}
