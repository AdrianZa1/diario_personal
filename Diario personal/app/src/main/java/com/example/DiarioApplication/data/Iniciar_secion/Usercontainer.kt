import android.content.Context
import com.example.inventory.data.AppDatabase
import com.example.inventory.data.OfflineUserRepository
import com.example.inventory.data.UserRepository



/**
 * App container for Dependency injection.
 */
interface UserContainer {
    val itemsRepository: UserRepository
}

/**
 * [UserContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class UserDataContainer(private val context: Context) : UserContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val itemsRepository: UserRepository by lazy {
        OfflineUserRepository (AppDatabase.getDatabase(context).userDao())
    }
}
