
package com.example.inventory.data

import com.tuapp.model.User
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val itemDao: UserDao) : UserRepository {
    override fun getAllUserStream(): Flow<List<User>> = itemDao.getAllItems()

    override fun getUsertream(id: Int): Flow<User?> = itemDao.getItem(id)

    override suspend fun insertUser( item:User) = itemDao.insert(item)

    override suspend fun deleteUser(item: User) = itemDao.delete(item)

    override suspend fun updateUser(item: User) = itemDao.update(item)
}
