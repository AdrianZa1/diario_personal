
package com.example.inventory.data

import com.tuapp.model.User
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val itemDao: UserDao)  {
    fun getAllUserStream(): Flow<List<User>> = itemDao.getAllUserStream()

     fun getUsertream(id: Int): Flow<User?> = itemDao.getUserStream(id)

     suspend fun insertUser( item:User) = itemDao.insertUser(item)

    suspend fun deleteUser(item: User) = itemDao.deleteUser(item)

    suspend fun updateUser(item: User) = itemDao.updateUser(item)
}
