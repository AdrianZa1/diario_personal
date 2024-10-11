package com.example.inventory.data

import com.tuapp.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    // Insertar un usuario
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    // Obtener todos los usuarios
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUserStream()
    }

    // Obtener un usuario por ID
    fun getUserById(userId: Int): Flow<User> {
        return userDao.getUserStream(userId)
    }

    // Eliminar un usuario por objeto
    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    // Actualizar un usuario
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
    suspend fun getUserByCredentials(email: String, password: String): User? {
        return userDao.getUserByCredentials(email, password)
    }
}
