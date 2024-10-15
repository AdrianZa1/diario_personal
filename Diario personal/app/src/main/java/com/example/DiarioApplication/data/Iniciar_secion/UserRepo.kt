
package com.example.inventory.data

import com.tuapp.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface UserRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllUserStream(): Flow<List<User>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getUsertream(id: Int): Flow<User?>

    /**
     * Insert item in the data source
     */
    suspend fun insertUser(item: User)

    /**
     * Delete item from the data source
     */
    suspend fun deleteUser(item: User)

    /**
     * Update item in the data source
     */
    suspend fun updateUser(item: User)
}